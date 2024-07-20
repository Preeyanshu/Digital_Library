package com.example.minorproject1.Services;

import com.example.minorproject1.DTOs.SearchBookRequest;
import com.example.minorproject1.Exceptions.BookLimitExceededException;
import com.example.minorproject1.Exceptions.BookNotFoundException;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.TransactionStatus;
import com.example.minorproject1.Models.Enums.TransactionType;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Models.Transaction;
import com.example.minorproject1.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.number_of_days}")
    private int timeLimit;

    @Value("${student.issue.max_books}")
    private int maxBooks;




//    public String initiateTxn(String bookName, int studentId, TransactionType transactionType) throws Exception {
//
//        switch (transactionType){
//            case ISSUE :
//                return issueTxn(bookName,studentId);
//            case RETURN:
//                return returnTxn(bookName,studentId);
//            default:
//                throw new Exception("Invalid transaction type");
//        }
//
//    }

    public String issueTxn(String bookName,int studentId) throws Exception {
        // first check whether this book is available or not

        List<Book> bookList;

        try {
            bookList = bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("name")
                            .searchValue(bookName)
                            .operator("=")
                            .available(true)
                            .build()
            );
        } catch (Exception e) {
            throw new Exception("Book not found");
        }

        Student student = studentService.get(studentId);

        //Validations

        //checking whether a student has issued the max no. of books
        if(student.getBookList() != null && student.getBookList().size() >= maxBooks){
            throw new BookLimitExceededException("Book limit reached");
        }

        if(bookList.isEmpty()){
            throw new BookNotFoundException("Not able to find any book in the library");
        }

        Book book = bookList.get(0);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            book.setStudent(student);
            bookService.assignBookToStudent(book,student);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }
        finally {
            return transactionRepository.save(transaction).getExternalTxnId();
        }

    }

    public String returnTxn(int bookId, int studentId) throws Exception {

        Book book;

        try {
            book = this.bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("id")
                            .searchValue(String.valueOf(bookId))
                            .build()
            ).get(0);
        }
        catch (Exception e){
            throw new Exception("Not able to fetch book details");
        }

        //Validations

        if (book.getStudent() == null || book.getStudent().getId() != studentId){
            throw new Exception("Book is not assigned to the student");
        }

        Student student = this.studentService.get(studentId);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        //Get the corresponding Issue transaction
        Transaction issueTransaction = this.transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(student, book, TransactionType.ISSUE,TransactionStatus.SUCCESS);
        //Fine calculation

        long issueTxnInMillis = issueTransaction.getTransactionTime().getTime();

        long currentTimeMillis = System.currentTimeMillis();

        long timeDifference = currentTimeMillis-issueTxnInMillis;

        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifference,TimeUnit.MILLISECONDS);

        Double fine = 0.0;
        if (timeDifferenceInDays > timeLimit){
            fine = (timeDifferenceInDays - timeLimit) * 1.0;
        }


        try {
            book.setStudent(null);
            this.bookService.unassignBookFromStudent(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction.getExternalTxnId();
        }
        catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }
        finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalTxnId();
        }
    }
}

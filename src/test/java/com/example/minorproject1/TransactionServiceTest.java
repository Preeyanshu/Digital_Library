package com.example.minorproject1;

import com.example.minorproject1.DTOs.SearchBookRequest;
import com.example.minorproject1.Exceptions.BookLimitExceededException;
import com.example.minorproject1.Exceptions.BookNotFoundException;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Models.Transaction;
import com.example.minorproject1.Repositories.TransactionRepository;
import com.example.minorproject1.Services.BookService;
import com.example.minorproject1.Services.StudentService;
import com.example.minorproject1.Services.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)  //how you want to execute your test cases
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    StudentService studentService;
    @Mock
    BookService bookService;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void issueTxnTest() throws Exception{

        String bookName = "ABC";
        int studentId = 1;
        String externalTxnId = UUID.randomUUID().toString();

        List<Book> bookList = Arrays.asList(
                Book.builder()
                        .id(1)
                        .name("ABC")
                        .build()
        );

        SearchBookRequest searchBookRequest = SearchBookRequest.builder()
                .searchKey("name")
                .searchValue(bookName)
                .operator("=")
                .available(true)
                .build();

        Student student = Student.builder()
                .id(1)
                .name("Peter")
                .build();

        Transaction transaction = Transaction.builder()
                .externalTxnId(externalTxnId)
                .student(student)
                .book(bookList.get(0))
                .build();

        when(bookService.search(any())).thenReturn(bookList);
        when(studentService.get(studentId)).thenReturn(student);

        when(transactionRepository.save(any())).thenReturn(transaction);
//        doNothing().when(bookService.assignBookToStudent(any(),any())).then;

        String actualTxnIdReturned = transactionService.issueTxn(bookName,studentId);


        // expected result == actual results

        Assert.assertEquals(externalTxnId,actualTxnIdReturned);

        verify(studentService,times(1)).get(studentId);
        verify(transactionRepository,times(2)).save(any());

    }


    @Test(expected = BookNotFoundException.class)
    public void issueTxn_BookNotFound() throws Exception {

        String bookName = "ABC";
        int studentId = 1;

        Student student = Student.builder()
                .id(1)
                .name("Peter")
                .build();

        when(bookService.search(any())).thenReturn(new ArrayList<>());
        when(studentService.get(studentId)).thenReturn(student);

        String actualTxnIdReturned = transactionService.issueTxn(bookName,studentId);
    }

    @Test(expected = BookLimitExceededException.class)
    public void issueTxn_BookLImitExceeded() throws Exception {
        String bookName = "ABC";
        int studentId = 1;

        List<Book> bookList = Arrays.asList(
                Book.builder()
                        .id(1)
                        .name("ABC")
                        .build()
        );

        Student student = Student.builder()
                .id(1)
                .name("Peter")
                .bookList(
                        Arrays.asList(
                                Book.builder()
                                        .id(4)
                                        .build(),
                                Book.builder()
                                        .id(5)
                                        .build(),
                                Book.builder()
                                        .id(6)
                                        .build(),
                                Book.builder()
                                        .id(7)
                                        .build()
                        )
                )
                .build();

        when(bookService.search(any())).thenReturn(bookList);
        when(studentService.get(studentId)).thenReturn(student);

        String actualTxnIdReturned = transactionService.issueTxn(bookName,studentId);

        verify(transactionRepository,times(0)).save(any());
        verify(bookService,times(0)).assignBookToStudent(any(),any());
    }

}

package com.example.minorproject1.Services;

import com.example.minorproject1.DTOs.CreateBookRequest;
import com.example.minorproject1.DTOs.SearchBookRequest;
import com.example.minorproject1.Models.Author;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.Genre;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    //createBook function => 2 or 3 DB calls depending on whether the author exists or not
    public Book createBook(CreateBookRequest createBookRequest) {

        Book book = createBookRequest.to();     // dto -> model conversion

        Author author = authorService.createOrGet(book.getMy_author());     //check whether this author exists int the author table or not, if it exists then simply get the author else create a new author

        book.setMy_author(author);

        return bookRepository.save(book);
    }

    public void assignBookToStudent(Book book, Student student){
        bookRepository.assignBookToStudent(book.getId(),student);
    }

    public void unassignBookFromStudent(Book book){
        bookRepository.unassignBookFromStudent(book.getId());
    }

    public Book delete(int bookId) {
        Book deletedBook = this.bookRepository.findById(bookId).orElse(null);
        bookRepository.deleteById(bookId);
        return deletedBook;
    }

    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception {
//        boolean isValidRequest = searchBookRequest.validate();
//        if(isValidRequest == false){
//            throw new Exception("Invalid Request");
//        }

        //String sql = "select b from book b where b.searchKey searchOperator searchValue";

        switch (searchBookRequest.getSearchKey()){
            case "name":
                if(searchBookRequest.isAvailable()){
                    return bookRepository.findByNameAndMy_StudentIsNull(searchBookRequest.getSearchValue());
                }

                return bookRepository.findByName(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id":
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            default:
                throw new Exception("Invalid search key");
        }

    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

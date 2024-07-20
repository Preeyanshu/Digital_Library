package com.example.minorproject1.Controllers;

import com.example.minorproject1.DTOs.BookResponse;
import com.example.minorproject1.DTOs.CreateBookRequest;
import com.example.minorproject1.DTOs.SearchBookRequest;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping("/post")
    public Book createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        return bookService.createBook(createBookRequest);
    }

    @DeleteMapping("/delete/{bookId}")
    public BookResponse deleteBook(@PathVariable("bookId") int bookId){
        return BookResponse.from(bookService.delete(bookId));
    }


    /**--SINGLE API TO HANDLE ALL THE SINGLE FILTERS NOT THE COMBINATIONS--**/
    // searchKey  operator  searchValue
    //  |            |           |
    // pages         <          500
    //author_name = Peter

    @GetMapping("/search")
    public List<Book> getBooks(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {

        return bookService.search(searchBookRequest);

    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

}

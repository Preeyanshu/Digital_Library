package com.example.minorproject1.DTOs;

import com.example.minorproject1.Models.Author;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.Genre;
import com.example.minorproject1.Models.Student;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private int id;
    private String name;
    private Genre genre;
    private Integer pages;
    private Author my_author;
    private Student my_student;
    private Date createdOn;
    private Date updatedOn;

    public static BookResponse from(Book b){
        return BookResponse.builder()
                .id(b.getId())
                .name(b.getName())
                .genre(b.getGenre())
                .createdOn(b.getCreatedOn())
                .updatedOn(b.getUpdatedOn())
                .pages(b.getPages())
                .my_author(b.getMy_author())
                .my_student(b.getStudent())
                .build();
    }
}

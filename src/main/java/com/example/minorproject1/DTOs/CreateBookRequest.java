package com.example.minorproject1.DTOs;

import com.example.minorproject1.Models.Author;
import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {
    @NotBlank
    private String name; //Book name
    @NotNull
    private Genre genre;
    private Integer pages;
    @NotBlank
    private String authorName;
    private String authorCountry;
    @NotBlank
    private String authorEmail;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .pages(this.pages)
                .my_author(
                        Author.builder()
                                .name(this.authorName)
                                .country(this.authorCountry)
                                .email(this.authorEmail)
                                .build()
                )
                .build();
    }
}

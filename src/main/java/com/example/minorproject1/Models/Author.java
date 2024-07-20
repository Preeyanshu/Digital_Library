package com.example.minorproject1.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String country;
    @Column(unique = true,nullable = false)
    private String email;
    @CreationTimestamp
    private Date addedOn;

    //Back reference
    @OneToMany(mappedBy = "my_author") //You don't need to create a column for the bookList in the author table, just create a back reference
    @JsonIgnoreProperties({"my_author"})
    private List<Book> bookList;
    //this back reference may lead to an infinite loop as book refers to the author and author refers back to the book
    //but this can be solved

}

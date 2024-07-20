package com.example.minorproject1.Models;

import com.example.minorproject1.Models.Enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Integer pages;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})     //it ignores the bookList property of author class hence not creating any infinite loop and also protecting the bidirectional relationship
    private Author my_author;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})
    private Student student;

    //Back reference
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)       //EAGER -> initializes the transaction object at run time
    @JsonIgnoreProperties({"book"})
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}

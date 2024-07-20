package com.example.minorproject1.Models;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contact;
    private Date validity;
    @CreationTimestamp
    private Date addedOn;
    @UpdateTimestamp
    private Date updatedOn;

    //Back references
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Book> bookList;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Transaction> transactionList;


    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"student"})
    private SecuredUser securedUser;

}

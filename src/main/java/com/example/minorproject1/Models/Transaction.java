package com.example.minorproject1.Models;

import com.example.minorproject1.Models.Enums.TransactionStatus;
import com.example.minorproject1.Models.Enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String externalTxnId;
    @CreationTimestamp
    private Date transactionTime;
    @UpdateTimestamp
    private Date updatedOn;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Book book;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Student student;

    private Double fine;

}

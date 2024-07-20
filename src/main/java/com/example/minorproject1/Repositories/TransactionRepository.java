package com.example.minorproject1.Repositories;

import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.TransactionStatus;
import com.example.minorproject1.Models.Enums.TransactionType;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    Transaction findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(
            Student student, Book book, TransactionType transactionType, TransactionStatus transactionStatus
            );

}

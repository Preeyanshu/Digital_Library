package com.example.minorproject1.Controllers;

import com.example.minorproject1.Models.Enums.TransactionType;
import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public String issueTxn(@RequestParam("name") String name,
                           @RequestParam("type")TransactionType transactionType) throws Exception {

        /**--TODO-- Need to add logic to retrieve student id from the security context
         * **/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();

        return transactionService.issueTxn(name,studentId);

    }

    @PostMapping("/return")
    public String returnTxn(@RequestParam("bookId") int bookId) throws Exception {

        /**--TODO-- Need to add logic to retrieve student id from the security context
         * **/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return transactionService.returnTxn(bookId,studentId);
    }



}



/**
 *  ISSUANCE
 *  1. Get the book details and student details //checking whether a book is available or not
 *  2. Validations  //
 *  3. Create a transaction with pending status
 *  4. Assign the book to that particular student   //UPDATE the book table set student_id = ?
 *  5. Update the transaction accordingly with status as SUCCESS or FAILED
 * **/

/**
 *  RETURN
 *  1. Create a transaction with pending status
 *  2. Check the due date adn correspondingly evaluate the fine
 *  3. Un-assign the book from the student      // UPDATE the book table set student_id = null
 *  4. Update the status as SUCCESS or FAILED
 * **/

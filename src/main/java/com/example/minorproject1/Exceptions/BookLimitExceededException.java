package com.example.minorproject1.Exceptions;

public class BookLimitExceededException extends Exception{
    public BookLimitExceededException(String msg) {
        super(msg);
    }
}

package com.example.minorproject1.Services;

import com.example.minorproject1.Models.Author;
import com.example.minorproject1.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public Author createOrGet(Author author){
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());

        if(authorFromDB != null){
            return authorFromDB;
        }

        return this.authorRepository.save(author);
    }

}

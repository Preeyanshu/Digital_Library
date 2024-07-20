package com.example.minorproject1.Repositories;

import com.example.minorproject1.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);   //similar to ( select * from author where email = ? )

}

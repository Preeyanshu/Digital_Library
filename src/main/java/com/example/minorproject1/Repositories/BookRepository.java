package com.example.minorproject1.Repositories;

import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.Enums.Genre;
import com.example.minorproject1.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("select b from Book b where b.name = :name and b.student is null")
    List<Book> findByNameAndMy_StudentIsNull(String name);

    List<Book> findByName(String name);

    List<Book> findByGenre(Genre genre);

    @Modifying      //for DML support
    @Transactional  //for updating any data
    @Query("update Book b set b.student = ?2 where b.id = ?1 and b.student is null")
    void assignBookToStudent(int bookId, Student student);

    @Modifying
    @Transactional
    @Query("update Book b set b.student = null where b.id = ?1")
    void unassignBookFromStudent(int bookId);
}

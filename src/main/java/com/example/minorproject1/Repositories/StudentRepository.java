package com.example.minorproject1.Repositories;

import com.example.minorproject1.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}

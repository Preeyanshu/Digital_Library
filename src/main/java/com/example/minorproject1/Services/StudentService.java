package com.example.minorproject1.Services;

import com.example.minorproject1.DTOs.CreateStudentRequest;
import com.example.minorproject1.DTOs.StudentResponse;
import com.example.minorproject1.DTOs.UpdateStudentRequest;
import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Repositories.StudentCacheRepository;
import com.example.minorproject1.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    @Value("${student.authorities}")
    String authorities;

    public Student create(CreateStudentRequest createStudentRequest) {
        Student student = createStudentRequest.to();

        SecuredUser securedUser = student.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));     // Set current password to new encoded password
        securedUser.setAuthorities(authorities);

        //create user
        securedUser = userService.save(securedUser);

        //create student
        student.setSecuredUser(securedUser);


        return studentRepository.save(student);
    }

    //Original get function
    public Student get(int studentId) {

        long start = System.currentTimeMillis();

        Student student = studentRepository.findById(studentId).orElse(null);

        long end = System.currentTimeMillis();
        System.out.println("Inside get function : time taken to retrieve the data : " + (end - start));

        return student;
    }


    //For comparing with cache
    public StudentResponse getResponse(int studentId) {

        long start = System.currentTimeMillis();

        Student student = studentRepository.findById(studentId).orElse(null);
        StudentResponse studentResponse = new StudentResponse(student);

        long end = System.currentTimeMillis();
        System.out.println("Inside get function : time taken to retrieve the data : " + (end - start));

        return studentResponse;
    }


    //With cache functionality
    public StudentResponse getUsingCache(int studentId) {
        long start = System.currentTimeMillis();

        StudentResponse studentResponse = studentCacheRepository.get(studentId);

        if (studentResponse == null){
            Student student = studentRepository.findById(studentId).orElse(null);

            studentResponse = new StudentResponse(student);


            studentCacheRepository.set(studentResponse);
        }

        long end = System.currentTimeMillis();
        System.out.println("Inside get function : time taken to retrieve the data : " + (end - start));

        return studentResponse;
    }

    public Student delete(int studentId) {
        Student deletedStudent = this.studentRepository.findById(studentId).orElse(null);
        studentRepository.deleteById(studentId);
        return deletedStudent;
    }

    public Student update(int studentId, UpdateStudentRequest updateStudentRequest) {
        // to be implemented
        return null;
    }
}

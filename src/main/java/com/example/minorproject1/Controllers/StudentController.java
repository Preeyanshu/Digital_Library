package com.example.minorproject1.Controllers;

import com.example.minorproject1.DTOs.CreateStudentRequest;
import com.example.minorproject1.DTOs.StudentResponse;
import com.example.minorproject1.DTOs.UpdateStudentRequest;
import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/post")
    public Student createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/get/{studentId}")     // This API can be invoked by admin only
    public Student getStudentForAdmin(@PathVariable("studentId") int studentId){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();


        return studentService.get(studentId);
    }


    @GetMapping("/details")     //This API return a student details who is calling this
    public Student getStudent(){

        /**--TODO-- Need to add logic to retrieve student id from the security context
         * **/

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();

        return studentService.get(studentId);
    }


    @DeleteMapping("/delete")
    public Student deleteStudent(){

        /**--TODO-- Need to add logic to retrieve student id from the security context
         * **/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.delete(studentId);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest){

        /**--TODO-- Need to add logic to retrieve student id from the security context
         * **/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.update(studentId, updateStudentRequest);

    }
}

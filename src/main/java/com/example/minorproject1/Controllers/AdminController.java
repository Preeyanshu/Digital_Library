package com.example.minorproject1.Controllers;

import com.example.minorproject1.DTOs.CreateAdminRequest;
import com.example.minorproject1.DTOs.CreateStudentRequest;
import com.example.minorproject1.Models.Admin;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/post")
    public Admin createStudent(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        return adminService.create(createAdminRequest);
    }


}

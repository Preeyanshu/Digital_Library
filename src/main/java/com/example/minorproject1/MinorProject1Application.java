package com.example.minorproject1;

import com.example.minorproject1.DTOs.CreateAdminRequest;
import com.example.minorproject1.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinorProject1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MinorProject1Application.class, args);
	}


	@Autowired
	AdminService adminService;

	@Override
	public void run(String... args) throws Exception {
//		CreateAdminRequest createAdminRequest = CreateAdminRequest.builder()
//				.name("Shantanu")
//				.username("shan@gmail.com")
//				.password("shantanu123")
//				.build();
//
//		adminService.create(createAdminRequest);
	}
}

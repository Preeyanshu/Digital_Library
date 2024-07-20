package com.example.minorproject1.Services;

import com.example.minorproject1.DTOs.CreateAdminRequest;
import com.example.minorproject1.Models.Admin;
import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminRepository;

    @Value("${admin.authorities}")
    String authorities;

    public Admin create(CreateAdminRequest createAdminRequest) {
        Admin admin = createAdminRequest.to();

        SecuredUser securedUser = admin.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));     // Set current password to new encoded password
        securedUser.setAuthorities(authorities);

        //create user
        securedUser = userService.save(securedUser);

        //create student
        admin.setSecuredUser(securedUser);


        return adminRepository.save(admin);
    }

}

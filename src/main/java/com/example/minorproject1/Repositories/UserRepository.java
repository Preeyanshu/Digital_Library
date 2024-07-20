package com.example.minorproject1.Repositories;

import com.example.minorproject1.Models.SecuredUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser,Integer> {

    SecuredUser findByUsername(String user);

}

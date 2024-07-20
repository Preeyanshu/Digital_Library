package com.example.minorproject1.DTOs;

import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Models.Student;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String contact;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .securedUser(
                        SecuredUser.builder()
                        .username(username)
                        .password(password)
                        .build()
                )
                .validity(new Date(System.currentTimeMillis() + 31536000000l))     //1 year form now
                .build();
    }

}

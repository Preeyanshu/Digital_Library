package com.example.minorproject1.DTOs;

import com.example.minorproject1.Models.Student;
import javax.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStudentRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String contact;

    private Date validity;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .validity(new Date(System.currentTimeMillis() + 365*24*60*60*1000))     //1 year form now
                .build();
    }

}

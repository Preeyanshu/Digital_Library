package com.example.minorproject1.DTOs;

import com.example.minorproject1.Models.Book;
import com.example.minorproject1.Models.SecuredUser;
import com.example.minorproject1.Models.Student;
import com.example.minorproject1.Models.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse implements Serializable {

    private int id;
    private String name;
    private String contact;
    private Date validity;
    private Date addedOn;
    private Date updatedOn;

    public StudentResponse(Student student){
        this.id = student.getId();
        this.name = student.getName();;
        this.contact = student.getContact();
        this.addedOn = student.getAddedOn();
        this.updatedOn = student.getUpdatedOn();
        this.validity = student.getValidity();
    }

}

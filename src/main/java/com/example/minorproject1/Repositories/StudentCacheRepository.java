package com.example.minorproject1.Repositories;

import com.example.minorproject1.DTOs.StudentResponse;
import com.example.minorproject1.Models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    private static final String STUDENT_PREFIX = "student::";

    public void set(StudentResponse student){
        if (student.getId() == 0){
            return;
        }

        String key = STUDENT_PREFIX + student.getId();
        redisTemplate.opsForValue().set(key,student,3600, TimeUnit.SECONDS);

    }

    public StudentResponse get(int studentId){
        if (studentId == 0){
            return null;
        }

        String key = STUDENT_PREFIX + studentId;
        return (StudentResponse) redisTemplate.opsForValue().get(key);

    }

}

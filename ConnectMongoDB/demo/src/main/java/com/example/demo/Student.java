package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Student {
    @Id
    private String id;
    private String name;
    private int age;
    private Gender gender;
}

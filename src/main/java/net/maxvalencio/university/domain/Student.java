package net.maxvalencio.university.domain;

import lombok.Data;

@Data
public class Student {

    private Long id;
    private String name;
    private String emailAddress;
    private int course;
}

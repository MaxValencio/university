package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Faculty {
    
    private Long id;
    private String name;
    private List<Teacher> teachers = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
}
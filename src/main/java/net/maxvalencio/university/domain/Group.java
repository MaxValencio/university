package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Group {

    private Long id;
    private String name;
    private int admissionYear;
    private List<Student> students = new ArrayList<>();
}
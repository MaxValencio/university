package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Teacher {

    private Long id;
    private String name;
    private String emailAddress;
    private String qualification;
    private List<Discipline> disciplines = new ArrayList<>();
}

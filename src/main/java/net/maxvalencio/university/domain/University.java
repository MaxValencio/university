package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class University {
    
    private String name;
    private List<Faculty> faculties = new ArrayList<>();
    private List<Audience> audiences = new ArrayList<>();
    private Schedule schedule = new Schedule();
}

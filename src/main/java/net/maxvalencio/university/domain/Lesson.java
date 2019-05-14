package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Lesson {

    private Long id;
    private Discipline discipline;
    private String dateStart;
    private String dateEnd;
    private Audience audience;
    private Teacher teacher;
    private List<Group> groups = new ArrayList<>();
}

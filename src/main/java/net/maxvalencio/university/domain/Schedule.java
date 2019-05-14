package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Schedule {

    private List<Lesson> lessons = new ArrayList<>();
}

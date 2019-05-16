package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Audience;
import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Lesson;
import net.maxvalencio.university.domain.Teacher;

public interface LessonDao {

    Lesson create(Discipline discipline, String dateStart, String dateEnd,
            Audience audience, Teacher teacher, List<Group> groups);
    
    Lesson getById(long id);
    
    Lesson update(long id, Discipline discipline, String dateStart, String dateEnd,
            Audience audience, Teacher teacher, List<Group> groups);
    
    boolean delete(long id);
    
    List<Lesson> getAll();
}

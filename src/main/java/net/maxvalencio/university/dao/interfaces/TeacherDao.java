package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Teacher;

public interface TeacherDao {

    Teacher create(String name, String emailAddress, String qualification, List<Discipline> disciplines);
    
    Teacher getById(long id);
    
    Teacher update(long id, String name, String emailAddress, String qualification, List<Discipline> disciplines);
    
    boolean delete(long id);
    
    List<Teacher> getAll();
}

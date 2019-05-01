package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Teacher;

public interface TeacherDAO {

    Teacher create(String name, String emailAddress, String qualification, List<Discipline> disciplines);
    
    Teacher create(String name, String emailAddress, String qualification, Discipline discipline);
    
    Teacher getById(long id);
    
    Teacher update(long id, String name, String emailAddress, String qualification, List<Discipline> disciplines);
    
    boolean addDiscipline(long id, long discipline_id);
    
    boolean deleteDiscipline(long id, long discipline_id);
    
    boolean delete(long id);
    
    List<Teacher> getAll();
}

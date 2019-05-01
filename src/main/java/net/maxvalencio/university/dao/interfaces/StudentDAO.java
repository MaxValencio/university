package net.maxvalencio.university.dao.interfaces;

import java.util.List;
import net.maxvalencio.university.domain.Student;

public interface StudentDAO {
    
    Student create(String name, String emailAddress, int course, long group_id);
    
    Student getById(long id);
    
    Student update(long id, String name, String emailAddress, int course, long group_id);
    
    boolean delete(long id);
    
    List<Student> getAll();
}

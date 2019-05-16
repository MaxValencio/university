package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Student;

public interface GroupDao {

    Group create(String name, int admissionYear,  List<Student> students);
    
    Group getById(long id);
    
    Group update(long id, String name, int admissionYear,  List<Student> students);
    
    boolean delete(long id);
    
    List<Group> getAll();
    
}

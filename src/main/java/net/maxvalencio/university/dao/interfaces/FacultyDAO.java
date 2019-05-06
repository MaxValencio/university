package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Faculty;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Teacher;

public interface FacultyDAO {
    
    Faculty create(String name, List<Teacher> teachers, List<Group> groups);
    
    Faculty getById(long id);
    
    Faculty getByName(String name);
    
    Faculty update(long id, String name, List<Teacher> teachers, List<Group> groups);
    
    boolean delete(long id);
    
    List<Faculty> getAll();
}

package net.maxvalencio.university.dao.interfaces;

import java.security.acl.Group;
import java.util.List;

import net.maxvalencio.university.domain.Faculty;
import net.maxvalencio.university.domain.Student;
import net.maxvalencio.university.domain.Teacher;

public interface FacultyDAO {
    
    boolean insertFaculty();
    
    Faculty getById(long id);
    
    Faculty getByName(String name);
    
    boolean update(long id);
    
    boolean delete(long id);
    
    List<Faculty> getAll();
    
    List<Teacher> getAllTeachers(long id);
    
    List<Group> getAllGroups(long id);
    
    List<Student> getAllStudents(long id);
}

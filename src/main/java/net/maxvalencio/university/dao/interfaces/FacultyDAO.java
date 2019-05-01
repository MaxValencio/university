package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Faculty;

public interface FacultyDAO {
    
    boolean insertFaculty();
    
    Faculty getById(int id);
    
    boolean updateFaculty(Faculty faculty);
    
    boolean delete(Faculty faculty);
    
    List<Faculty> getAll();
}

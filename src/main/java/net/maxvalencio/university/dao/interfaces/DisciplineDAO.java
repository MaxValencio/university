package net.maxvalencio.university.dao.interfaces;

import java.util.List;

import net.maxvalencio.university.domain.Discipline;

public interface DisciplineDAO {
    
    Discipline create(String name);
    
    Discipline getById(long id);
    
    Discipline getByName(String name);
    
    Discipline update(long id, String name);
    
    boolean delete(long id);
    
    List<Discipline> getAll();
}

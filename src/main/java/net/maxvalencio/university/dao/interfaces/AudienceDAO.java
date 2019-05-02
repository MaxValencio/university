package net.maxvalencio.university.dao.interfaces;

import java.util.List;
import net.maxvalencio.university.domain.Audience;

public interface AudienceDAO {
    
    Audience create(int number);
    
    Audience getById(long id);
    
    Audience getByNumber(int number);
    
    List<Audience> getAll();
    
    boolean update(long id);
    
    boolean delete(long id);
    
    
}

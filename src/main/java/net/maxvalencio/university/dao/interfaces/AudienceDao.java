package net.maxvalencio.university.dao.interfaces;

import java.util.List;
import net.maxvalencio.university.domain.Audience;

public interface AudienceDao {
    
    Audience create(int number);
    
    Audience getById(long id);
    
    Audience getByNumber(int number);
    
    Audience update(long id, int number);
    
    boolean delete(long id);
    
    List<Audience> getAll();  
}

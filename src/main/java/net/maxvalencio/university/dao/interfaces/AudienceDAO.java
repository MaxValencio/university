package net.maxvalencio.university.dao.interfaces;

import java.util.List;
import net.maxvalencio.university.domain.Audience;

public interface AudienceDAO {
    
    boolean create();
    
    Audience getById(int id);
    
    List<Audience> getAll();
    
    boolean update(Audience audience);
    
    boolean delete(Audience audience);
    
    
}

package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.AudienceDAO;
import net.maxvalencio.university.domain.Audience;

public class PostgreSQLAudienceDAOImpl implements AudienceDAO{

    DAOFactory daoFactory = new DAOFactory();
    
    @Override
    public Audience create(int number) {
        String sql = "INSERT INTO audience(number) VALUES(?);";
        
        Audience audience = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, number);
            statement.executeUpdate();
            
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            
            audience = new Audience();
            audience.setId(resultSet.getLong("id"));
            audience.setNumber(resultSet.getInt("number"));
            return audience;
        } catch (SQLException e) {
            System.err.println("Error in create() method of PostgrSQLDisciplineDAOImpl class");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }  
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in create() "
                        + "method of PostgreSQLDisciplineDAOImpl class");
            }               
        }
        return null;
    }

    @Override
    public Audience getById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Audience getByNumber(int number) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Audience> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(long id) {
        // TODO Auto-generated method stub
        return false;
    }

}

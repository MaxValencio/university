package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            System.err.println("Error in create() method of PostgrSQLAudienceDAOImpl class");
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
                        + "method of PostgreSQLAudienceDAOImpl class");
            }               
        }
        return null;
    }

    @Override
    public Audience getById(long id) {
        String sql = "SELECT * FROM audiences WHERE id = ?;";
        
        Audience  audience = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                audience  = new Audience();
                audience.setId(resultSet.getLong("id"));
                audience.setNumber(resultSet.getInt("number"));
                return audience;
            }
        } catch (SQLException e) {
           System.err.println("Error in getById() method of PostgrSQLAudienceDAOImpl class");
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
                System.err.println("Cannot execute close connection in getById() "
                        + "method of PostgreSQLAudienceDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Audience getByNumber(int number) {
        String sql = "SELECT * FROM audiences WHERE number = ?;";
        
        Audience audience = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, number);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) { 
                audience = new Audience();
                audience.setId(resultSet.getLong("id"));
                audience.setNumber(resultSet.getInt("number"));
                return audience;
            }
        }  catch (SQLException e) {
            System.err.println("Error in getByNumber() method of PostgrSQLDAudienceDAOImpl class");
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
                 System.err.println("Cannot execute close connection in getByNumber() "
                         + "method of PostgreSQLAudienceDAOImpl class");
            }
        }
        return null;
    }
    
    @Override
    public Audience update(long id, int number) {
        String sql = "UPDATE audiences SET number = ? WHERE id = ?;";
        
        Audience audience = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, number);
            statement.setLong(2, id);
            
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                audience = new Audience();
                audience.setId(resultSet.getLong("id"));
                audience.setNumber(resultSet.getInt("number"));
                return audience;
            }
        } catch (SQLException e) {
            System.err.println("Error in update() method of PostgrSQLAudienceDAOImpl class");
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
                System.err.println("Cannot execute close connection in update() "
                        + "method of PostgreSQLAudienceDAOImpl class");
            }
        }
        return null;
    }
 
    @Override
    public boolean delete(long id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
           
            int i = statement.executeUpdate("DELETE FROM audiences WHERE id = " + id + ";");
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in delete() method of PostgrSQLAudiencesDAOImpl class"); 
            
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in delete() "
                        + "method of PostgreSQLAudiencesDAOImpl class");
            }
        }
        return false;
    }


    @Override
    public List<Audience> getAll() {
        String sql = "SELECT * FROM audiences;";
        
        List<Audience> audiences =null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            audiences = new ArrayList<>();
            
            while(resultSet.next()) {
                Audience audience = new Audience();
                audience.setId(resultSet.getLong("id"));
                audience.setNumber(resultSet.getInt("number"));
                audiences.add(audience);
            }
            return audiences;
        } catch (SQLException e) {
            System.err.println("Error in getAll() method of PostgrSQLAudienceDAOImpl class");
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
                System.err.println("Cannot execute close connection in getAll() "
                        + "method of PostgreSQLAudienceDAOImpl class");
            }
        }
        return null;
    }
}

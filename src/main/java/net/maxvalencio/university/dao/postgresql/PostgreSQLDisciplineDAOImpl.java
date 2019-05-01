package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.DisciplineDAO;
import net.maxvalencio.university.domain.Discipline;

public class PostgreSQLDisciplineDAOImpl implements DisciplineDAO {
    
    private DAOFactory daoFactory = new DAOFactory();
    
    @Override
    public Discipline create(String name) {
        String sql = "INSERT INTO disciplines(name) VALUES(?);";
        
        Discipline discipline = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.executeUpdate();
            
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            
            discipline = new Discipline();
            discipline.setId(resultSet.getLong("id"));
            discipline.setName(resultSet.getString("name"));
            return discipline;
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
    public Discipline getById(long id) {
        String sqlSelect = "SELECT * FROM disciplines WHERE id = ?;";
        
        Discipline discipline = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sqlSelect);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()) { 
                discipline = new Discipline();
                discipline.setId(resultSet.getLong("id"));
                discipline.setName(resultSet.getString("name"));
            }
            return discipline;
            
        }  catch (SQLException e) {
            System.err.println("Error in getById() method of PostgrSQLDisciplineDAOImpl class");
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
                         + "method of PostgreSQLDisciplineDAOImpl class");
             }
         }
        return null;
    }

    @Override
    public Discipline getByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Discipline update(long id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(long id) {
       
        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
           
            int i = statement.executeUpdate("DELETE FROM disciplines WHERE id = " + id + ";");
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in delete() method of PostgrSQLDisciplineDAOImpl class"); 
            
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
                        + "method of PostgreSQLDisciplineDAOImpl class");
            }
        }
        return false;
    }

    @Override
    public List<Discipline> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static void main (String[] args) {
        PostgreSQLDisciplineDAOImpl disc = new PostgreSQLDisciplineDAOImpl();
        //disc.create("Програмная инженерия");
        System.out.println(disc.delete(10));
    }

}

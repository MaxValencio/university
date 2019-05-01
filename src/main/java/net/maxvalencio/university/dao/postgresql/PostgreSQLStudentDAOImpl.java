package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.StudentDAO;
import net.maxvalencio.university.domain.Student;

public class PostgreSQLStudentDAOImpl implements StudentDAO {
    
    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Student create(String name, String emailAddress, int course, long group_id) {
        String sql = "INSERT INTO students(name, emailAddress, course, group_id) VALUES( ?, ?, ?, ?);";
        
        Student student = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setInt(3, course);
            statement.setLong(4, group_id);
            statement.executeQuery();
            
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            
            student  = new Student();
            student.setId(resultSet.getLong("id"));
            student.setName(resultSet.getString("name"));
            student.setEmailAddress(resultSet.getString("emailAddress"));
            student.setCourse(resultSet.getInt("course")); 
            student.setGroupId(resultSet.getLong("group_id"));
        } catch (SQLException e) {
           System.err.println("Error in create() method of PostgrSQLStudentDAOImpl class");
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
                        + "method of PostgreSQLStudentDAOImpl class");
            }
        }
        return student;
    }

    @Override
    public Student getById(long id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        Student student = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student  = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setEmailAddress(resultSet.getString("emailAddress"));
                student.setCourse(resultSet.getInt("course"));
                student.setGroupId(resultSet.getLong("group_id"));
            }
        } catch (SQLException e) {
           System.err.println("Error in getById() method of PostgrSQLStudentDAOImpl class");
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
                        + "method of PostgreSQLStudentDAOImpl class");
            }
        }
        return student;
        
    }

    @Override
    public Student update(long id, String name, String emailAddress, int course, long group_id) {
        String sql = "UPDATE students SET name = ?, emailAddress = ?, course = ?, group_id = ? WHERE id = ?;";
        
        Student student = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setInt(3, course);
            statement.setLong(4, group_id);
            statement.setLong(5, id);
            statement.executeUpdate();
            
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            
            student = new Student();
            student.setName(name);
            student.setEmailAddress(emailAddress);
            student.setCourse(course);
            student.setGroupId(group_id);
            
        } catch (SQLException e) {
            System.err.println("Error in update() method of PostgrSQLStudentDAOImpl class");
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
                        + "method of PostgreSQLStudentDAOImpl class");
            }
        }
        return student;
    }

    @Override
    public boolean delete(long id) {
        
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate("DELETE FROM students WHERE id =" + id);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in delete() method of PostgrSQLStudentDAOImpl class");
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
                        + "method of PostgreSQLStudentDAOImpl class");
            }
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
       String sql = "SELECT * FROM students;";
       
       List<Student> students =null;
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;
       try {
           connection = daoFactory.getConnection();
           statement = connection.createStatement();
           resultSet = statement.executeQuery(sql);
           
           students = new ArrayList<>();
           
           while(resultSet.next()) {
               Student student = new Student();
               student.setId(resultSet.getLong("id"));
               student.setName(resultSet.getString("name"));
               student.setEmailAddress(resultSet.getString("emailAddress"));
               student.setCourse(resultSet.getInt("course"));
               student.setGroupId(resultSet.getLong("group_id"));
               students.add(student);
           }
           return students;
       } catch (SQLException e) {
           System.err.println("Error in getAll() method of PostgrSQLStudentDAOImpl class");
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
                       + "method of PostgreSQLStudentDAOImpl class");
           }
       }
       return null;
    }
}

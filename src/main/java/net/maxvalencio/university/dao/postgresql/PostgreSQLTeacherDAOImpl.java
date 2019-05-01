package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.TeacherDAO;
import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Teacher;

public class PostgreSQLTeacherDAOImpl implements TeacherDAO {
    
    DAOFactory daoFactory = new DAOFactory();

    @Override
    public Teacher create(String name, String emailAddress,
            String qualification, List<Discipline> disciplines) {
        String sqlTeachers = "INSERT INTO teachers(name, emailAddress, qualification) VALUES (?, ?, ?);";
        String sqlTeachersDisciplines = "INSERT INTO teachers_disciplines(teacher_id, discipline_id) VALUES (?, ?);";
            
        Teacher teacher = null;
        Connection connection = null;
        PreparedStatement insertTeachers = null;
        PreparedStatement insertTeachersDisciplines = null;
        ResultSet resultSet = null;
        
        try {
            connection = daoFactory.getConnection();
            insertTeachers = connection.prepareStatement(sqlTeachers, Statement.RETURN_GENERATED_KEYS);
            insertTeachers.setString(1, name);
            insertTeachers.setString(2, emailAddress);
            insertTeachers.setString(3, qualification);
            insertTeachers.executeUpdate();
           
            resultSet = insertTeachers.getGeneratedKeys(); 
            resultSet.next();
            long generatedId = resultSet.getLong("id");
   
            insertTeachersDisciplines = connection.prepareStatement(sqlTeachersDisciplines);
            for(Discipline discipline : disciplines) {
                insertTeachersDisciplines.setLong(1, generatedId);
                insertTeachersDisciplines.setLong(2, discipline.getId());
                insertTeachersDisciplines.addBatch();
            }
            insertTeachersDisciplines.executeBatch();
            
            teacher = getById(generatedId);
            return teacher;
            
        } catch (SQLException e) {
            System.err.println("Error");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertTeachers != null) {
                    insertTeachers.close();
                }
                if (insertTeachersDisciplines != null) {
                    insertTeachersDisciplines.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in create() "
                        + "method of PostgreSQLTeacherDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Teacher create(String name, String emailAddress,
            String qualification, Discipline discipline) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Teacher getById(long id) {
        String sqlTeachers = "SELECT * FROM teachers WHERE id = ?;";
        String sqlTeachersDisciplines = "SELECT * FROM teachers_disciplines where teacher_id = ?;";
        
        Teacher teacher = null;
        Connection connection = null;
        PreparedStatement selectTeachers = null;
        PreparedStatement selectTeachersDisciplines = null;
        ResultSet rsTeachers = null;
        ResultSet rsTeachersDisciplines = null;
        
        try {
            connection = daoFactory.getConnection();
            selectTeachers = connection.prepareStatement(sqlTeachers);
            selectTeachers.setLong(1, id);
            
            rsTeachers = selectTeachers.executeQuery();

            if (rsTeachers.next()) {
                teacher  = new Teacher();
                teacher.setId(rsTeachers.getLong("id"));
                teacher.setName(rsTeachers.getString("name"));
                teacher.setEmailAddress(rsTeachers.getString("emailAddress"));
                teacher.setQualification(rsTeachers.getString("qualification"));
            }
            
            selectTeachersDisciplines = connection.prepareStatement(sqlTeachersDisciplines);
            selectTeachersDisciplines.setLong(1, id);
            
            rsTeachersDisciplines = selectTeachersDisciplines.executeQuery();

            List<Discipline> teacherDisciplines = new ArrayList<>();
            PostgreSQLDisciplineDAOImpl daoDiscipline = new PostgreSQLDisciplineDAOImpl();
            
            while(rsTeachersDisciplines.next()) {
                long disciplineId = rsTeachersDisciplines.getLong("discipline_id");
                Discipline discipline = daoDiscipline.getById(disciplineId);
                teacherDisciplines.add(discipline);
            }
            teacher.setDisciplines(teacherDisciplines);
            return teacher;
        } catch (SQLException e) {
           System.err.println("Error in getById() method of PostgrSQLTeacherDAOImpl class");
        } finally {
            try {
                if (rsTeachers != null) {
                    rsTeachers.close();
                }
                if (selectTeachers != null) {
                    selectTeachers.close();
                }
                if (rsTeachersDisciplines != null) {
                    rsTeachersDisciplines.close();
                }
                if (selectTeachersDisciplines != null) {
                    selectTeachersDisciplines.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getById() "
                        + "method of PostgreSQLTeacherDAOImpl class");
            }
        }
        return null;   
    }

    @Override
    public Teacher update(long id, String name, String emailAddress,
            String qualification, List<Discipline> disciplines) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addDiscipline(long id, long discipline_id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteDiscipline(long id, long discipline_id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Teacher> getAll() {
        String sqlSelectTeachers = "SELECT id FROM teachers;";
        
        List<Teacher> teachers = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelectTeachers);
            
            teachers = new ArrayList<>();
            
            while(resultSet.next()) {
                long teacherId = resultSet.getLong("id");
                Teacher teacher = getById(teacherId);
                teachers.add(teacher);
            }
            return teachers;
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
    
    public static void main(String[] args) {
        PostgreSQLTeacherDAOImpl teacher = new PostgreSQLTeacherDAOImpl();
        List<Teacher> teachers = teacher.getAll();
        for(Teacher teach : teachers) {
            System.out.println(teach.toString());
        }
    }
}

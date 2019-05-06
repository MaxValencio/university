package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.FacultyDAO;
import net.maxvalencio.university.domain.Faculty;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Teacher;

public class PostgreSQLFacultyDAOImpl implements FacultyDAO{

    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Faculty create(String name, List<Teacher> teachers,
            List<Group> groups) {
        String sglInsertFaculties = "INSERT INTO faculties(name) VALUES (?);";
        String sqlInsertFacultiesTeachers = "INSERT INTO faculties_teachers(faculty_id, teacher_id) VALUES (?, ?);";
        String sqlInsertFacultiesGroups ="INSERT INTO faculties_groups(faculty_id, groups_id) VALUES (?, ?);";
        
        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement insertFaculties = null;
        PreparedStatement insertFacultiesTeachers = null;
        PreparedStatement insertFacultiesGroups = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            insertFaculties = connection.prepareStatement(sglInsertFaculties, Statement.RETURN_GENERATED_KEYS);
            insertFaculties.setString(1, name);
            insertFaculties.executeUpdate();
            
            resultSet = insertFaculties.getGeneratedKeys();
            resultSet.next();
            long facultyId = resultSet.getLong("id");
            
            insertFacultiesTeachers = connection.prepareStatement(sqlInsertFacultiesTeachers);
            for (Teacher teacher : teachers) {
                insertFacultiesTeachers.setLong(1, facultyId);
                insertFacultiesTeachers.setLong(2, teacher.getId());
                insertFacultiesTeachers.addBatch();
            }
            insertFacultiesTeachers.executeBatch();
            
            insertFacultiesGroups = connection.prepareStatement(sqlInsertFacultiesGroups);
            for (Group group : groups) {
                insertFacultiesGroups.setLong(1, facultyId);
                insertFacultiesGroups.setLong(2, group.getId());
                insertFacultiesGroups.addBatch();
            }
            insertFacultiesGroups.executeBatch();
            
            faculty = getById(facultyId);
            return faculty;
        } catch (SQLException e) {
            System.err.println("Error in create() method of PostgrSQLFacultyDAOImpl class");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertFaculties != null) {
                    insertFaculties.close();
                }
                if (insertFacultiesTeachers != null) {
                    insertFacultiesTeachers.close();
                }
                if (insertFacultiesGroups != null) {
                    insertFacultiesGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in create() "
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }   
        return null;
    }

    @Override
    public Faculty getById(long id) {
        String sqlFaculties = "SELECT * FROM faculty WHERE id = ?;";
        String sqlFacultiesTeachers = "SELECT * FROM faculties_teachers WHERE faculty_id = ?;";
        String sqlFacultiesGroups = "SELECT * FROM faculties_groups WHERE faculty_id = ?;";
        
        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement stFaculties = null;
        PreparedStatement stFacultiesTeachers = null;
        PreparedStatement stFacultiesGroups = null;
        ResultSet rsFaculties = null;
        ResultSet rsFacultiesTeachers = null;
        ResultSet rsFacultiesGroups = null;
        try {
            connection = daoFactory.getConnection();
            stFaculties = connection.prepareStatement(sqlFaculties);
            stFaculties.setLong(1, id);

            rsFaculties = stFaculties.executeQuery();

            if (rsFaculties.next()) {
                faculty = new Faculty();
                faculty.setId(rsFaculties.getLong("id"));
                faculty.setName(rsFaculties.getString("name"));

                stFacultiesTeachers = connection.prepareStatement(sqlFacultiesTeachers);
                stFacultiesTeachers.setLong(1, id);

                rsFacultiesTeachers = stFacultiesTeachers.executeQuery();
                
                List<Teacher> teachers = new ArrayList<>();
                PostgreSQLTeacherDAOImpl daoTeacher = new PostgreSQLTeacherDAOImpl();

                while (rsFacultiesTeachers.next()) {
                    long teacherId = rsFacultiesTeachers.getLong("teacher_id");
                    Teacher teacher = daoTeacher.getById(teacherId);
                    teachers.add(teacher);
                }
                faculty.setTeachers(teachers);
                
                stFacultiesGroups = connection.prepareStatement(sqlFacultiesGroups);
                stFacultiesGroups.setLong(1, id);

                rsFacultiesGroups = stFacultiesGroups.executeQuery();
                
                List<Group> groups = new ArrayList<>();
                PostgreSQLGroupDAOImpl daoGroup = new PostgreSQLGroupDAOImpl();

                while (rsFacultiesTeachers.next()) {
                    long groupId = rsFacultiesGroups.getLong("group_id");
                    Group group = daoGroup.getById(groupId);
                    groups.add(group);
                }
                faculty.setGroups(groups);
                return faculty;
            }
        } catch (SQLException e) {
            System.err.println("Error in getById() method of PostgrSQLFacultyDAOImpl class");
        } finally {
            try {
                if (rsFaculties != null) {
                    rsFaculties.close();
                }
                if (stFaculties != null) {
                    stFaculties.close();
                }
                if (rsFacultiesTeachers != null) {
                    rsFacultiesTeachers.close();
                }
                if (stFacultiesTeachers != null) {
                    stFacultiesTeachers.close();
                }
                if (rsFacultiesGroups != null) {
                    rsFacultiesGroups.close();
                }
                if (stFacultiesGroups != null) {
                    stFacultiesGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getById() "
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Faculty getByName(String name) {
        String sqlFaculties = "SELECT * FROM faculty WHERE name = ?;";
        String sqlFacultiesTeachers = "SELECT * FROM faculties_teachers WHERE faculty_id = ?;";
        String sqlFacultiesGroups = "SELECT * FROM faculties_groups WHERE faculty_id = ?;";
        
        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement stFaculties = null;
        PreparedStatement stFacultiesTeachers = null;
        PreparedStatement stFacultiesGroups = null;
        ResultSet rsFaculties = null;
        ResultSet rsFacultiesTeachers = null;
        ResultSet rsFacultiesGroups = null;
        try {
            connection = daoFactory.getConnection();
            stFaculties = connection.prepareStatement(sqlFaculties);
            stFaculties.setString(1, name);

            rsFaculties = stFaculties.executeQuery();

            if (rsFaculties.next()) {
                faculty = new Faculty();
                long facultyId = rsFaculties.getLong("id");
                faculty.setId(facultyId);
                faculty.setName(rsFaculties.getString("name"));

                stFacultiesTeachers = connection.prepareStatement(sqlFacultiesTeachers);
                stFacultiesTeachers.setLong(1, facultyId);

                rsFacultiesTeachers = stFacultiesTeachers.executeQuery();
                
                List<Teacher> teachers = new ArrayList<>();
                PostgreSQLTeacherDAOImpl daoTeacher = new PostgreSQLTeacherDAOImpl();

                while (rsFacultiesTeachers.next()) {
                    long teacherId = rsFacultiesTeachers.getLong("teacher_id");
                    Teacher teacher = daoTeacher.getById(teacherId);
                    teachers.add(teacher);
                }
                faculty.setTeachers(teachers);
                
                stFacultiesGroups = connection.prepareStatement(sqlFacultiesGroups);
                stFacultiesGroups.setLong(1, facultyId);

                rsFacultiesGroups = stFacultiesGroups.executeQuery();
                
                List<Group> groups = new ArrayList<>();
                PostgreSQLGroupDAOImpl daoGroup = new PostgreSQLGroupDAOImpl();

                while (rsFacultiesTeachers.next()) {
                    long groupId = rsFacultiesGroups.getLong("group_id");
                    Group group = daoGroup.getById(groupId);
                    groups.add(group);
                }
                faculty.setGroups(groups);
                return faculty;
            }
        } catch (SQLException e) {
            System.err.println("Error in getName() method of PostgrSQLFacultyDAOImpl class");
        } finally {
            try {
                if (rsFaculties != null) {
                    rsFaculties.close();
                }
                if (stFaculties != null) {
                    stFaculties.close();
                }
                if (rsFacultiesTeachers != null) {
                    rsFacultiesTeachers.close();
                }
                if (stFacultiesTeachers != null) {
                    stFacultiesTeachers.close();
                }
                if (rsFacultiesGroups != null) {
                    rsFacultiesGroups.close();
                }
                if (stFacultiesGroups != null) {
                    stFacultiesGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getName() "
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Faculty update(long id, String name, List<Teacher> teachers, List<Group> groups) {
        String sqlFaculties = "UPDATE faculties SET name = ? WHERE id = ?;";
        String sqlFacultiesTeachers = "UPDATE faculties_teachers SET teacher_id = ? WHERE faculty_id = ?;";
        String sqlFacultiesGroups = "UPDATE faculties_groups SET groups_id = ? WHERE faculty_id = ?;";

        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement stFaculties = null;
        PreparedStatement stFacultiesTeachers = null;
        PreparedStatement stFacultiesGroups = null;
        try {
            connection = daoFactory.getConnection();
            stFaculties = connection.prepareStatement(sqlFaculties);
            stFaculties.setString(1, name);
            stFaculties.setLong(2, id);
            stFaculties.executeQuery();

            stFacultiesTeachers = connection.prepareStatement(sqlFacultiesTeachers);
            for (Teacher teacher : teachers) {
                stFacultiesTeachers.setLong(1, teacher.getId());
                stFacultiesTeachers.setLong(2, id);
                stFacultiesTeachers.addBatch();
            }
            stFacultiesTeachers.executeBatch();
            
            stFacultiesGroups = connection.prepareStatement(sqlFacultiesGroups);
            for (Group group : groups) {
                stFacultiesGroups.setLong(1, group.getId());
                stFacultiesGroups.setLong(2, id);
                stFacultiesGroups.addBatch();
            }
            stFacultiesGroups.executeBatch();

            faculty = getById(id);
            return faculty;
        } catch (SQLException e) {
            System.err.println("Error in update() method of PostgrSQLFacultyDAOImpl class");
        } finally {
            try {
                if (stFaculties != null) {
                    stFaculties.close();
                }
                if (stFacultiesTeachers != null) {
                    stFacultiesTeachers.close();
                }
                if (stFacultiesGroups != null) {
                    stFacultiesGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in update() "
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM faculties WHERE id = " + id + ";"; 
        
        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in delete() method of PostgrSQLFacultyDAOImpl class");
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
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }
        return false;
    }

    @Override
    public List<Faculty> getAll() {
        String sql = "SELECT id FROM faculties;";

        List<Faculty> faculties = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            faculties = new ArrayList<>();

            while (resultSet.next()) {
                long facultyId = resultSet.getLong("id");
                Faculty faculty = getById(facultyId);
                faculties.add(faculty);
            }
            return faculties;
        } catch (SQLException e) {
            System.err.println("Error in getAll() method of PostgrSQLFacultyDAOImpl class");
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
                        + "method of PostgreSQLFacultyDAOImpl class");
            }
        }
        return null;
    }
}

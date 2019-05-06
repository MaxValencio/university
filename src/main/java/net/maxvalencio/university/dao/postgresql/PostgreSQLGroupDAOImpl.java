package net.maxvalencio.university.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.GroupDAO;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Student;

public class PostgreSQLGroupDAOImpl implements GroupDAO {

    DAOFactory daoFactory = new DAOFactory();
    
    @Override
    public Group create(String name, int year,
            List<Student> students) {
        String sqlGroup = "INSERT INTO group(name, year) VALUES (?, ?);";
        String sqlGroupsStudents = "INSERT INTO groups_students(group_id, students_id) VALUES (?, ?);";

        Group group = null;
        Connection connection = null;
        PreparedStatement insertGroups = null;
        PreparedStatement insertGroupsStudents = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            insertGroups = connection.prepareStatement(sqlGroup, Statement.RETURN_GENERATED_KEYS);
            insertGroups.setString(1, name);
            insertGroups.setInt(2, year);
            insertGroups.executeUpdate();

            resultSet = insertGroups.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong("id");

            insertGroupsStudents = connection.prepareStatement(sqlGroupsStudents);
            for (Student student : students) {
                insertGroupsStudents.setLong(1, generatedId);
                insertGroupsStudents.setLong(2, student.getId());
                insertGroupsStudents.addBatch();
            }
            insertGroupsStudents.executeBatch();

            group = getById(generatedId);
            return group;
        } catch (SQLException e) {
            System.err.println("Error in create() method of PostgrSQLGroupDAOImpl class");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertGroups != null) {
                    insertGroups.close();
                }
                if (insertGroupsStudents != null) {
                    insertGroupsStudents.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in create() "
                        + "method of PostgreSQLGroupDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Group getById(long id) {
        String sqlGroups = "SELECT * FROM groups WHERE id = ?;";
        String sqlGroupsStudents = "SELECT * FROM groups_students WHERE group_id = ?;";

        Group group = null;
        Connection connection = null;
        PreparedStatement selectGroups = null;
        PreparedStatement selectGroupsStudents = null;
        ResultSet rsGroups = null;
        ResultSet rsGroupsStudents = null;

        try {
            connection = daoFactory.getConnection();
            selectGroups = connection.prepareStatement(sqlGroups);
            selectGroups.setLong(1, id);

            rsGroups = selectGroups.executeQuery();

            if (rsGroups.next()) {
                group = new Group();
                group.setId(rsGroups.getLong("id"));
                group.setName(rsGroups.getString("name"));
                group.setAdmissionYear(rsGroups.getInt("year"));

                selectGroupsStudents = connection.prepareStatement(sqlGroupsStudents);
                selectGroupsStudents.setLong(1, id);

                rsGroupsStudents = selectGroupsStudents.executeQuery();

                List<Student> students = new ArrayList<>();
                PostgreSQLStudentDAOImpl daoStudent = new PostgreSQLStudentDAOImpl();

                while (rsGroupsStudents.next()) {
                    long studentId = rsGroupsStudents.getLong("student_id");
                    Student student = daoStudent.getById(studentId);
                    students.add(student);
                }
                
                group.setStudents(students);
                return group;
            }
        } catch (SQLException e) {
            System.err.println("Error in getById() method of PostgrSQLGroupDAOImpl class");
        } finally {
            try {
                if (rsGroups != null) {
                    rsGroups.close();
                }
                if (selectGroups != null) {
                    selectGroups.close();
                }
                if (rsGroupsStudents != null) {
                    rsGroupsStudents.close();
                }
                if (selectGroupsStudents != null) {
                    selectGroupsStudents.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getById() "
                        + "method of PostgreSQLGroupDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Group update(long id, String name, int year, List<Student> students) {
        String sqlGroups = "UPDATE groups SET name = ?, year = ? WHERE id = ?;";
        String sqlGroupsStudents = "UPDATE groups_teachers SET student_id = ? WHERE group_id = ?;";

        Group group = null;
        Connection connection = null;
        PreparedStatement updateGroups = null;
        PreparedStatement updateGroupsStudents = null;
        try {
            connection = daoFactory.getConnection();
            updateGroups = connection.prepareStatement(sqlGroups);
            updateGroups.setString(1, name);
            updateGroups.setInt(2, year);
            updateGroups.setLong(3, id);
            updateGroups.executeQuery();

            updateGroupsStudents = connection.prepareStatement(sqlGroupsStudents);
            for (Student student : students) {
                updateGroupsStudents.setLong(1, student.getId());
                updateGroupsStudents.setLong(2, id);
                updateGroupsStudents.addBatch();
            }
            updateGroupsStudents.executeBatch();

            group = getById(id);
            return group;
        } catch (SQLException e) {
            System.err.println("Error in update() method of PostgrSQLGroupDAOImpl class");
        } finally {
            try {
                if (updateGroups != null) {
                    updateGroups.close();
                }
                if (updateGroupsStudents != null) {
                    updateGroupsStudents.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in update() "
                        + "method of PostgreSQLGroupDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM groups WHERE id = " + id + ";"; 
        
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
            System.err.println("Error in delete() method of PostgrSQLGroupDAOImpl class");
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
                        + "method of PostgreSQLGroupDAOImpl class");
            }
        }
        return false;
    }

    @Override
    public List<Group> getAll() {
        String sql = "SELECT id FROM groups;";

        List<Group> groups = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            groups = new ArrayList<>();

            while (resultSet.next()) {
                long groupId = resultSet.getLong("id");
                Group group = getById(groupId);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            System.err.println("Error in getAll() method of PostgrSQLGroupDAOImpl class");
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
                        + "method of PostgreSQLGroupDAOImpl class");
            }
        }
        return null;
    }
}

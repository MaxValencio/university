package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.GroupDao;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Student;

import static net.maxvalencio.university.dao.DaoUtils.*;

public class JdbcGroupDaoImpl implements GroupDao {

    DaoFactory daoFactory = new DaoFactory();

    @Override
    public Group create(String name, int year, List<Student> students) {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            long groupId = insertGroups(connection, name, year);
            insertGroupsStudents(connection, groupId, students);
            return getById(groupId);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcGroupDaoImpl class");
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    private Long insertGroups(Connection connection, String name, int year)
            throws SQLException {
        final String SQL_INSERT_GROUPS = "INSERT INTO group(name, year) VALUES (?, ?);";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_GROUPS,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, year);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            System.err.println("Error inserting into the groups table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private void insertGroupsStudents(Connection connection, long groupId,
            List<Student> students) throws SQLException {
        final String SQL_INSERT_GROUPS_STUDENTS = "INSERT INTO groups_students(group_id, students_id) VALUES (?, ?);";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_GROUPS_STUDENTS);
            for (Student student : students) {
                statement.setLong(1, groupId);
                statement.setLong(2, student.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err
                    .println("Error inserting into the groups_students table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public Group getById(long id) {
        final String SQL_SELECT_GROUPS = "SELECT * FROM groups WHERE id = ?;";

        Group group = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_GROUPS);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getLong("id"));
                group.setName(resultSet.getString("name"));
                group.setAdmissionYear(resultSet.getInt("year"));
                group.setStudents(getGroupStudents(connection, id));
                return group;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcGroupDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private List<Student> getGroupStudents(Connection connection, long id)
            throws SQLException {
        final String SQL_SELECT_GROUPS_STUDENTS = "SELECT * FROM groups_students WHERE group_id = ?;";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_GROUPS_STUDENTS);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            List<Student> students = new ArrayList<>();
            JdbcStudentDaoImpl daoStudent = new JdbcStudentDaoImpl();

            while (resultSet.next()) {
                long studentId = resultSet.getLong("student_id");
                Student student = daoStudent.getById(studentId);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            System.err
                    .println("Error selecting from the groups_students table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    @Override
    public Group update(long id, String name, int year,
            List<Student> students) {
        final String SQL_UPDATE_GROUPS = "UPDATE groups SET name = ?, year = ? WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_GROUPS);
            statement.setString(1, name);
            statement.setInt(2, year);
            statement.setLong(3, id);
            statement.executeQuery();

            updateGroupsStudents(connection, id, students);
            return getById(id);
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcGroupDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return null;
    }

    private void updateGroupsStudents(Connection connection, long id,
            List<Student> students) throws SQLException {
        final String SQL_UPDATE_GROUPS_STUDENTS = "UPDATE groups_teachers SET student_id = ? WHERE group_id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_GROUPS_STUDENTS);
            for (Student student : students) {
                statement.setLong(1, student.getId());
                statement.setLong(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err
                    .println("Error selecting from the groups_students table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM groups WHERE id = " + id + ";";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(SQL_DELETE);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in delete() method of JdbcGroupDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Group> getAll() {
        final String SQL_SELECT = "SELECT id FROM groups;";

        List<Group> groups = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);

            groups = new ArrayList<>();

            while (resultSet.next()) {
                long groupId = resultSet.getLong("id");
                Group group = getById(groupId);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcGroupDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

package net.maxvalencio.university.dao;

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

import static net.maxvalencio.university.dao.DAOUtils.*;

public class JdbcFacultyDaoImpl implements FacultyDAO {

    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Faculty create(String name, List<Teacher> teachers,
            List<Group> groups) {
        Connection connection = null;
        try {
            long facultyId = insertFaculties(connection, name);
            insertTeachers(connection, teachers, facultyId);
            insertGroups(connection, groups, facultyId);
            return getById(facultyId);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcFacultyDaoImpl class");
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    private Long insertFaculties(Connection connection, String name)
            throws SQLException {
        final String SQL_INSERT_FACULTIES = "INSERT INTO faculties(name) VALUES (?);";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_FACULTIES,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            System.err.println("Error inserting into the faculties table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private void insertTeachers(Connection connection, List<Teacher> teachers,
            long facultyId) throws SQLException {
        final String SQL_INSERT_FACULTIES_TEACHERS = "INSERT INTO faculties_teachers(faculty_id, teacher_id) VALUES (?, ?);";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_INSERT_FACULTIES_TEACHERS);
            for (Teacher teacher : teachers) {
                statement.setLong(1, facultyId);
                statement.setLong(2, teacher.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println(
                    "Error inserting into the faculties_teachers table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    private void insertGroups(Connection connection, List<Group> groups,
            long facultyId) throws SQLException {
        final String SQL_INSERT_FACULTIES_GROUPS = "INSERT INTO faculties_groups(faculty_id, groups_id) VALUES (?, ?);";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_INSERT_FACULTIES_GROUPS);
            for (Group group : groups) {
                statement.setLong(1, facultyId);
                statement.setLong(2, group.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err
                    .println("Error inserting into the faculties_groups table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public Faculty getById(long id) {
        final String SQL_SELECT_FACULTIES = "SELECT * FROM faculties WHERE id = ?;";

        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FACULTIES);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(resultSet.getLong("id"));
                faculty.setName(resultSet.getString("name"));
                faculty.setTeachers(getTeachers(connection, id));
                faculty.setGroups(getGroups(connection, id));
                return faculty;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcFacultyDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private List<Teacher> getTeachers(Connection connection, Long id)
            throws SQLException {
        final String SQL_SELECT_FACULTIES_TEACHERS = "SELECT * FROM faculties_teachers WHERE faculty_id = ?;";

        List<Teacher> teachers = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
                    .prepareStatement(SQL_SELECT_FACULTIES_TEACHERS);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            teachers = new ArrayList<>();
            JdbcTeacherDaoImpl daoTeacher = new JdbcTeacherDaoImpl();

            while (resultSet.next()) {
                long teacherId = resultSet.getLong("teacher_id");
                teachers.add(daoTeacher.getById(teacherId));
            }
            return teachers;
        } catch (SQLException e) {
            System.err.println(
                    "Error selecting into the faculties_teachers table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private List<Group> getGroups(Connection connection, Long id)
            throws SQLException {
        final String SQL_SELECT_FACULTIES_GROUPS = "SELECT * FROM faculties_groups WHERE faculty_id = ?;";

        List<Group> groups = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
                    .prepareStatement(SQL_SELECT_FACULTIES_GROUPS);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            groups = new ArrayList<>();
            JdbcGroupDaoImpl daoGroup = new JdbcGroupDaoImpl();

            while (resultSet.next()) {
                long groupId = resultSet.getLong("group_id");
                groups.add(daoGroup.getById(groupId));
            }
            return groups;
        } catch (SQLException e) {
            System.err
                    .println("Error selecting into the faculties_groups table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    @Override
    public Faculty getByName(String name) {
        final String SQL_SELECT_FACULTIES = "SELECT * FROM faculty WHERE name = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_FACULTIES);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getById(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getName() method of JdbcFacultyDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Faculty update(long id, String name, List<Teacher> teachers,
            List<Group> groups) {
        Faculty faculty = null;
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            updateFaculties(connection, id, name);
            updateFacultiesTeachers(connection, id, teachers);
            updateFacultiesGroups(connection, id, groups);
            faculty = getById(id);
            return faculty;
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcFacultyDaoImpl class");
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    private void updateFaculties(Connection connection, Long id, String name)
            throws SQLException {
        final String SQL_UPDATE_FACULTIES = "UPDATE faculties SET name = ? WHERE id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_FACULTIES);
            statement.setString(1, name);
            statement.setLong(2, id);
            statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error updating in faculties table");
        } finally {
            closeStatement(statement);
        }
    }

    private void updateFacultiesTeachers(Connection connection, Long id,
            List<Teacher> teachers) throws SQLException {
        final String SQL_UPDATE_FACULTIES_TEACHERS = "UPDATE faculties_teachers SET teacher_id = ? WHERE faculty_id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_UPDATE_FACULTIES_TEACHERS);
            for (Teacher teacher : teachers) {
                statement.setLong(1, teacher.getId());
                statement.setLong(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println(
                    "Error updating into the faculties_teachers table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    private void updateFacultiesGroups(Connection connection, Long id,
            List<Group> groups) throws SQLException {
        final String SQL_UPDATE_FACULTIES_GROUPS = "UPDATE faculties_groups SET groups_id = ? WHERE faculty_id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_UPDATE_FACULTIES_GROUPS);
            for (Group group : groups) {
                statement.setLong(1, group.getId());
                statement.setLong(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err
                    .println("Error updating into the faculties_groups table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM faculties WHERE id = " + id
                + ";";

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
                    "Error in delete() method of JdbcFacultyDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Faculty> getAll() {
        final String SQL_SELECT_ALL = "SELECT id FROM faculties;";

        List<Faculty> faculties = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL);
            faculties = new ArrayList<>();
            while (resultSet.next()) {
                long facultyId = resultSet.getLong("id");
                Faculty faculty = getById(facultyId);
                faculties.add(faculty);
            }
            return faculties;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcFacultyDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

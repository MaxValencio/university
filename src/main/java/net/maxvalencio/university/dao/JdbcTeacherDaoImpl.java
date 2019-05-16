package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.TeacherDao;
import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Teacher;

import static net.maxvalencio.university.dao.DaoUtils.*;

public class JdbcTeacherDaoImpl implements TeacherDao {

    DaoFactory daoFactory = new DaoFactory();

    @Override
    public Teacher create(String name, String emailAddress,
            String qualification, List<Discipline> disciplines) {
        Connection connection = null;
        try {
            long teacherId = insertTeacher(connection, name, emailAddress,
                    qualification);
            insertDisciplines(connection, disciplines, teacherId);
            return getById(teacherId);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcTeacherDaoImpl class");
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    private Long insertTeacher(Connection connection, String name,
            String emailAddress, String qualification) throws SQLException {
        final String SQL_INSERT_TEACHERS = "INSERT INTO teachers(name, emailAddress, qualification) VALUES (?, ?, ?);";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_TEACHERS,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setString(3, qualification);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException e) {
            System.err.println("Error inserting into the teachers table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    private void insertDisciplines(Connection connection,
            List<Discipline> disciplines, long teacherId) throws SQLException {
        final String SQL_INSERT_TEACHERS_DISCIPLINES = "INSERT INTO teachers_disciplines(teacher_id, discipline_id) VALUES (?, ?);";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_INSERT_TEACHERS_DISCIPLINES);
            for (Discipline discipline : disciplines) {
                statement.setLong(1, teacherId);
                statement.setLong(2, discipline.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println(
                    "Error inserting into the teachers_disciplines table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public Teacher getById(long id) {
        final String SQL_SELECT_TEACHERS = "SELECT * FROM teachers WHERE id = ?;";

        Teacher teacher = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_TEACHERS);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(resultSet.getLong("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setEmailAddress(resultSet.getString("emailAddress"));
                teacher.setQualification(resultSet.getString("qualification"));
                teacher.setDisciplines(getDisciplines(connection, id));
                return teacher;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcTeacherDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private List<Discipline> getDisciplines(Connection connection, long id)
            throws SQLException {
        final String SQL_SELECT_TEACHERS_DISCIPLINES = "SELECT * FROM teachers_disciplines WHERE teacher_id = ?;";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection
                    .prepareStatement(SQL_SELECT_TEACHERS_DISCIPLINES);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            List<Discipline> disciplines = new ArrayList<>();
            JdbcDisciplineDaoImpl daoDiscipline = new JdbcDisciplineDaoImpl();

            while (resultSet.next()) {
                long disciplineId = resultSet.getLong("discipline_id");
                Discipline discipline = daoDiscipline.getById(disciplineId);
                disciplines.add(discipline);
            }
            return disciplines;
        } catch (SQLException e) {
            System.err.println(
                    "Error selecting into the teachers_disciplines table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    @Override
    public Teacher update(long id, String name, String emailAddress,
            String qualification, List<Discipline> disciplines) {

        Connection connection = null;
        try {
            updateTeachers(connection, id, name, emailAddress, qualification);
            updateTeachersDisciplines(connection, id, disciplines);
            return getById(id);
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcTeacherDaoImpl class");
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    private void updateTeachers(Connection connection, long id, String name,
            String emailAddress, String qualification) throws SQLException {
        final String SQL_UPDATE_TEACHERS = "UPDATE teachers SET name = ?, emailAddress = ?, qualification = ? WHERE id = ?;";

        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_TEACHERS);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setString(3, qualification);
            statement.setLong(4, id);
            statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error updating into the teachers table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    private void updateTeachersDisciplines(Connection connection, long id,
            List<Discipline> disciplines) throws SQLException {
        final String SQL_UPDATE_TEACHERS_DISCIPLINES = "UPDATE teachers_disciplines SET discipline_id = ? WHERE teacher_id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(SQL_UPDATE_TEACHERS_DISCIPLINES);
            for (Discipline discipline : disciplines) {
                statement.setLong(1, discipline.getId());
                statement.setLong(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println(
                    "Error updating into the teachers_disciplines table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM teachers WHERE id = " + id + ";";

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
                    "Error in delete() method of JdbcTeacherDaoImpl class");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<Teacher> getAll() {
        final String SQL_SELECT = "SELECT id FROM teachers;";

        List<Teacher> teachers = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);

            teachers = new ArrayList<>();

            while (resultSet.next()) {
                long teacherId = resultSet.getLong("id");
                Teacher teacher = getById(teacherId);
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcTeacherDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

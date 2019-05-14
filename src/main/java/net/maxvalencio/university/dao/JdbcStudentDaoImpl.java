package net.maxvalencio.university.dao;

import static net.maxvalencio.university.dao.DAOUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.StudentDAO;
import net.maxvalencio.university.domain.Student;

public class JdbcStudentDaoImpl implements StudentDAO {

    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Student create(String name, String emailAddress, int course) {
        final String SQL_INSERT = "INSERT INTO students(name, emailAddress, course) VALUES( ?, ?, ?);";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setInt(3, course);
            statement.executeQuery();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return getStudent(resultSet);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcStudentDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        try {
            Student student = new Student();
            student.setId(resultSet.getLong("id"));
            student.setName(resultSet.getString("name"));
            student.setEmailAddress(resultSet.getString("emailAddress"));
            student.setCourse(resultSet.getInt("course"));
            return student;
        } catch (SQLException e) {
            System.err.println("Error extraction from the students table");
            throw new SQLException(e);
        }
    }

    @Override
    public Student getById(long id) {
        final String SQL_SELECT = "SELECT * FROM students WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getStudent(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcStudentDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Student update(long id, String name, String emailAddress,
            int course) {
        final String SQL_UPDATE = "UPDATE students SET name = ?, emailAddress = ?, course = ? WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, name);
            statement.setString(2, emailAddress);
            statement.setInt(3, course);
            statement.setLong(4, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getStudent(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcStudentDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM students WHERE id =" + id + ";";

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
                    "Error in delete() method of JdbcStudentDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        final String SQL_SELECT = "SELECT * FROM students;";

        List<Student> students = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);

            students = new ArrayList<>();

            while (resultSet.next()) {
                Student student = getStudent(resultSet);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcStudentDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

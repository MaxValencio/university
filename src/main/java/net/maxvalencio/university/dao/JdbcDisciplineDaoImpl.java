package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.DisciplineDAO;
import net.maxvalencio.university.domain.Discipline;

import static net.maxvalencio.university.dao.DAOUtils.*;

public class JdbcDisciplineDaoImpl implements DisciplineDAO {

    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Discipline create(String name) {
        final String SQL_INSERT = "INSERT INTO disciplines(name) VALUES(?);";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return getDiscipline(resultSet);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcDisciplineDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private Discipline getDiscipline(ResultSet resultSet) throws SQLException {
        try {
            Discipline discipline = new Discipline();
            discipline.setId(resultSet.getLong("id"));
            discipline.setName(resultSet.getString("name"));
            return discipline;
        } catch (SQLException e) {
            System.err.println("Error extraction from the disciplines table");
            throw new SQLException(e);
        }
    }

    @Override
    public Discipline getById(long id) {
        final String SQL_SELECT = "SELECT * FROM disciplines WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getDiscipline(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcDisciplineDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Discipline getByName(String name) {
        final String SQL_SELECT = "SELECT * FROM disciplines WHERE name = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getDiscipline(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getByName() method of JdbcDisciplineDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Discipline update(long id, String name) {
        final String SQL_UPDATE = "UPDATE disciplines SET name = ? WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, name);
            statement.setLong(2, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getDiscipline(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcDisciplineDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM disciplines WHERE id = " + id
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
                    "Error in delete() method of JdbcDisciplineDaoImpl class");

        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Discipline> getAll() {
        final String SQL_SELECT = "SELECT * FROM disciplines;";

        List<Discipline> disciplines = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);
            disciplines = new ArrayList<>();
            while (resultSet.next()) {
                disciplines.add(getDiscipline(resultSet));
            }
            return disciplines;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcDisciplineDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

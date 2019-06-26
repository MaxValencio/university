package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.AudienceDao;
import net.maxvalencio.university.domain.Audience;

import static net.maxvalencio.university.dao.DaoUtils.*;

public class JdbcAudienceDaoImpl implements AudienceDao {

    DaoFactory daoFactory = new DaoFactory();

    @Override
    public Audience create(int number) {
        final String SQL_INSERT = "INSERT INTO audience(number) VALUES(?);";

        try (Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, number);
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                return getAudienceDB(resultSet);
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcAudienceDaoImpl class");
        }
        return null;
    }

    private Audience getAudienceDB(ResultSet resultSet) throws SQLException {
        try {
            Audience audience = new Audience();
            audience.setId(resultSet.getLong("id"));
            audience.setNumber(resultSet.getInt("number"));
            return audience;
        } catch (SQLException e) {
            System.err.println("Error extraction from the audiences table");
            throw new SQLException(e);
        }
    }

    @Override
    public Audience getById(long id) {
        final String SQL_SELECT = "SELECT * FROM audiences WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getAudienceDB(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcAudienceDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Audience getByNumber(int number) {
        final String SQL_SELECT = "SELECT * FROM audiences WHERE number = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            statement.setInt(1, number);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getAudienceDB(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getByNumber() method of JdbcAudienceDaoImpll class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public Audience update(long id, int number) {
        final String SQL_UPDATE = "UPDATE audiences SET number = ? WHERE id = ?;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, number);
            statement.setLong(2, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getAudienceDB(resultSet);
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcAudienceDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM audiences WHERE id = " + id
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
                    "Error in delete() method of JdbcAudienceDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Audience> getAll() {
        final String SQL_SELECT = "SELECT * FROM audiences;";

        List<Audience> audiences = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);
            audiences = new ArrayList<>();
            while (resultSet.next()) {
                audiences.add(getAudienceDB(resultSet));
            }
            return audiences;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcAudienceDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}

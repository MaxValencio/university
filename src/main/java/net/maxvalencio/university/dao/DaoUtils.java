package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtils {
    
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Can't close ResultSet");
                e.printStackTrace();
            }
        }
    }
    
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Can't close Statement");
                e.printStackTrace();
            }
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Can't close Connection");
                e.printStackTrace();
            }
        }
    }
    
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }
    
    public static void close(Statement statement, Connection connection) {
        closeStatement(statement);
        closeConnection(connection);
    }
}

package net.maxvalencio.university.dao.postgresql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;

public class DAOFactory {
    
    public Connection getConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("info.properties"));
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(url, user, password);
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File info.properties not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error load info.properties file", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connection to Database", e);
        }
    }   
}

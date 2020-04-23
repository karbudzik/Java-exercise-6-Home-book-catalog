package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.postgresql.Driver;

public class ConnectionInitializer {
    private final String filepath = "src/main/resources/database.properties";
    private Properties properties;
    private String url;
    private String user;
    private String password;

    public ConnectionInitializer() {
        readProperties();
        readDatabaseLoginCredentials();
    }

    private void readProperties() {
        properties = new Properties();
        Path path = Paths.get(filepath);
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDatabaseLoginCredentials() {
        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
    }

    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}


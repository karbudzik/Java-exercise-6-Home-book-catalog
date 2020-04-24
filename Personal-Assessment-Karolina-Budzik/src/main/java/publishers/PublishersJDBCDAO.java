package publishers;

import connection.ConnectionInitializer;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublishersJDBCDAO implements PublishersDAO {
    private Connection getConnection() {
        ConnectionInitializer initializer = new ConnectionInitializer();
        return initializer.getConnection();
    }

    @Override
    public void insertPublisher(Publisher publisher) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "INSERT INTO publishers VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, publisher.getID());
            statement.setString(2, publisher.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    @Override
    public void updatePublisherName(Publisher publisher) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "UPDATE publishers SET name = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getID());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    @Override
    public boolean checkIfIDInDatabase(String ID) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM publishers WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    @Override
    public boolean checkIfNameInDatabase(String name) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM publishers WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    @Override
    public Optional<Publisher> getPublisherByID(String ID) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM publishers WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(extractPublisherFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    @Override
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM publishers";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Publisher publisher = extractPublisherFromResultSet(resultSet);
                publishers.add(publisher);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
        return publishers;
    }

    @Override
    public void deleteAllPublishersWithoutBooks() {
        try (Connection connection = getConnection()) {
            String psqlStatement = "DELETE FROM publishers p WHERE NOT EXISTS (SELECT FROM books b WHERE b.publisher_id = p.id);";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database.");
        }
    }

    private Publisher extractPublisherFromResultSet(ResultSet resultSet) throws SQLException {
        return new Publisher(resultSet.getString("id"),
                resultSet.getString("name"));
    }
}

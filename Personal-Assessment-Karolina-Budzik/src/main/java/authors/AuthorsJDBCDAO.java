package authors;

import connection.ConnectionInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsJDBCDAO implements AuthorsDAO {
    private Connection getConnection() {
        ConnectionInitializer initializer = new ConnectionInitializer();
        return initializer.getConnection();
    }

    @Override
    public void insertAuthor(Author author) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "INSERT INTO authors (first_name, surname) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }
    }

    @Override
    public void updateAuthor(Author author) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "UPDATE authors SET first_name = ?, surname = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setInt(3, author.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }
    }

    @Override
    public boolean checkIfAuthorIdInDatabase(int ID) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM authors WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }
        return false;
    }

    @Override
    public Author getAuthorById(int ID) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM authors WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractAuthorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM authors";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Author author = extractAuthorFromResultSet(resultSet);
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }

        return authors;
    }

    @Override
    public void deleteAllAuthorsWithoutBooks() {
        try (Connection connection = getConnection()) {
            String psqlStatement = "DELETE FROM authors a WHERE NOT EXISTS (SELECT FROM books b WHERE b.author_id = a.id);";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
        }
    }

    private Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("surname"));
    }
}

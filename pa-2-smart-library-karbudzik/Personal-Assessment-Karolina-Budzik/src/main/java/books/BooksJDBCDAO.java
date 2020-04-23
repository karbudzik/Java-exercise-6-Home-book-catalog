package books;

import connection.ConnectionInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksJDBCDAO implements BooksDAO {

    private Connection getConnection() {
        ConnectionInitializer initializer = new ConnectionInitializer();
        return initializer.getConnection();
    }

    @Override
    public void insertBook(Book book) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "INSERT INTO books VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setLong(1, book.getISBN());
            statement.setInt(2, book.getAuthorID());
            statement.setString(3, book.getTitle());
            statement.setString(4, book.getPublisherID());
            statement.setInt(5, book.getPublicationYear());
            statement.setFloat(6, book.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "UPDATE books SET author_id = ?, title = ?, publisher_id = ?, publication_year = ?, price = ? WHERE ISBN = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setInt(1, book.getAuthorID());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getPublisherID());
            statement.setInt(4, book.getPublicationYear());
            statement.setFloat(5, book.getPrice());
            statement.setLong(6, book.getISBN());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookISBN(long oldISBN, long newISBN) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "UPDATE books SET ISBN = ? WHERE ISBN = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setLong(1, newISBN);
            statement.setLong(7, oldISBN);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfISBNInDatabase(long ISBN) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM books WHERE ISBN = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setLong(1, ISBN);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> library = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = extractBookFromResultSet(resultSet);
                library.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public List<Book> getBooksByAuthorID(int authorID) {
        List<Book> library = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM books WHERE author_id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setInt(1, authorID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = extractBookFromResultSet(resultSet);
                library.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public List<Book> getBooksByPublisherID(String publisherId) {
        List<Book> library = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String psqlStatement = "SELECT * FROM books WHERE publisher_id = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setString(1, publisherId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = extractBookFromResultSet(resultSet);
                library.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public void deleteBook(long ISBN) {
        try (Connection connection = getConnection()) {
            String psqlStatement = "DELETE FROM books WHERE ISBN = ?;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.setLong(1, ISBN);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllBooks() {
        try (Connection connection = getConnection()) {
            String psqlStatement = "DELETE FROM books;";
            PreparedStatement statement = connection.prepareStatement(psqlStatement);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Sorry, couldn't connect to database.");
            e.printStackTrace();
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("ISBN"),
                resultSet.getInt("author_id"),
                resultSet.getString("title"),
                resultSet.getString("publisher_id"),
                resultSet.getInt("publication_year"),
                resultSet.getFloat("price"));
    }
}
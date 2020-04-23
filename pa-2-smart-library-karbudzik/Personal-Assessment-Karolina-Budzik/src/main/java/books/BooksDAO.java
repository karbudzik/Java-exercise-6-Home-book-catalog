package books;

import java.util.List;

public interface BooksDAO {

    void insertBook(Book book);

    void updateBook(Book book);

    void updateBookISBN(long oldISBN, long newISBN);

    boolean checkIfISBNInDatabase(long ISBN);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthorID(int authorID);

    List<Book> getBooksByPublisherID(String publisherId);

    void deleteBook(long ISBN);

    void deleteAllBooks();
}

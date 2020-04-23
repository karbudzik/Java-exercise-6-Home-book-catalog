package authors;

import java.util.List;

public interface AuthorsDAO {
    void insertAuthor(Author author);

    void updateAuthor(Author author);

    boolean checkIfAuthorIdInDatabase(int ISBN);

    Author getAuthorById(int ID);

    List<Author> getAllAuthors();

    void deleteAllAuthorsWithoutBooks();
}

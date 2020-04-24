package authors;

import java.util.List;
import java.util.Optional;

public interface AuthorsDAO {
    void insertAuthor(Author author);

    void updateAuthor(Author author);

    boolean checkIfAuthorIdInDatabase(int ISBN);

    Optional<Author> getAuthorById(int ID);

    List<Author> getAllAuthors();

    void deleteAllAuthorsWithoutBooks();
}

package publishers;

import java.util.List;
import java.util.Optional;

public interface PublishersDAO {
    void insertPublisher(Publisher publisher);

    void updatePublisherName(Publisher publisher);

    boolean checkIfIDInDatabase(String ID);

    boolean checkIfNameInDatabase(String name);

    Optional<Publisher> getPublisherByID(String ID);

    List<Publisher> getAllPublishers();

    void deleteAllPublishersWithoutBooks();
}

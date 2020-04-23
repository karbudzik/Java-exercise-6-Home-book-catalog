package publishers;

import java.util.List;

public interface PublishersDAO {
    void insertPublisher(Publisher publisher);

    void updatePublisherName(Publisher publisher);

    boolean checkIfIDInDatabase(String ID);

    boolean checkIfNameInDatabase(String name);

    Publisher getPublisherByID(String ID);

    List<Publisher> getAllPublishers();

    void deleteAllPublishersWithoutBooks();
}

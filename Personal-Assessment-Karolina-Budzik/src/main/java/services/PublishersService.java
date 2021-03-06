package services;

import authors.Author;
import books.BooksDAO;
import interactions.InputManager;
import interactions.View;
import publishers.Publisher;
import publishers.PublishersDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PublishersService extends Service {
    private final BooksDAO booksDAO;
    private final PublishersDAO publishersDAO;

    public PublishersService(BooksDAO booksDAO, PublishersDAO publishersDAO) {
        super();
        this.booksDAO = booksDAO;
        this.publishersDAO = publishersDAO;
    }

    @Override
    void initializeMenu() {
        menuOptions = new ArrayList<>(Arrays.asList("Show all publishers", "Show all books of specified publisher", "Add new publisher",
                "Change publisher's name", "Delete publisher without any books", "Go back to main menu"));
    }

    @Override
    void manageUserChoice(int choice) {
        switch (choice) {
            case 1:
                view.printPublishers(publishersDAO.getAllPublishers());
                break;
            case 2:
                coordinateShowingPublisherDetails();
                break;
            case 3:
                coordinateAddingNewPublisher();
                break;
            case 4:
                coordinateModifyingPublisherName();
                break;
            case 6:
                publishersDAO.deleteAllPublishersWithoutBooks();
        }
    }

    private void coordinateShowingPublisherDetails() {
        view.printPublishers(publishersDAO.getAllPublishers());
        String publisherID = getExistingPublisherId();
        Optional<Publisher> maybe = publishersDAO.getPublisherByID(publisherID);
        if (maybe.isPresent()) {
            Publisher publisher = maybe.get();
            view.print(String.format("All books published by %s:", publisher.getName()));
            view.printBooks(booksDAO.getBooksByPublisherID(publisherID));
        } else {
            view.print("Sorry, we couldn't show this publisher's details!");
        }
    }

    private void coordinateAddingNewPublisher() {
        String ID = inputManager.getStringInput("Please type the ID of the publisher");
        while (publishersDAO.checkIfIDInDatabase(ID)) {
            ID = inputManager.getStringInput("This ID already exists. Please try different one:");
        }
        String name = inputManager.getStringInput("Please type the name of the publisher");
        while (publishersDAO.checkIfNameInDatabase(name)) {
            name = inputManager.getStringInput("This name already exists. Please try different one:");
        }
        Publisher publisher = new Publisher(ID, name);
        publishersDAO.insertPublisher(publisher);
    }

    private void coordinateModifyingPublisherName() {
        view.printPublishers(publishersDAO.getAllPublishers());
        String ID = getExistingPublisherId();
        String name = inputManager.getStringInput("Type the publisher's new name");
        Publisher publisher = new Publisher(ID, name);
        publishersDAO.updatePublisherName(publisher);
    }

    private String getExistingPublisherId() {
        String publisherID = inputManager.getStringInput("Please type the ID of the publisher you choose. Pick from the list above");
        while (!publishersDAO.checkIfIDInDatabase(publisherID)) {
            publisherID = inputManager.getStringInput("Wrong ID. Please try again:");
        }
        return publisherID;
    }

    private String getExistingPublisherName() {
        String publisherName = inputManager.getStringInput("Please type the name of the publisher you choose. Pick from the list above");
        while (!publishersDAO.checkIfNameInDatabase(publisherName)) {
            publisherName = inputManager.getStringInput("Wrong name. Please try again:");
        }
        return publisherName;
    }
}

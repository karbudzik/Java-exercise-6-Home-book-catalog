package services;

import authors.Author;
import authors.AuthorsDAO;
import books.BooksDAO;
import interactions.InputManager;
import interactions.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorsService extends Service {
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;

    public AuthorsService(BooksDAO booksDAO, AuthorsDAO authorsDAO) {
        super();
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
    }

    @Override
    void initializeMenu() {
        menuOptions = new ArrayList<>(Arrays.asList("Show all authors", "Show all books of specified author", "Add new author",
                "Change author's details", "Delete authors without any books", "Go back to main menu"));
    }

    @Override
    void manageUserChoice(int choice) {
        switch (choice) {
            case 1:
                view.printAuthors(authorsDAO.getAllAuthors());
                break;
            case 2:
                coordinateShowingAuthorsDetails();
                break;
            case 3:
                coordinateAddingNewAuthor();
                break;
            case 4:
                coordinateModifyingAuthor();
                break;
            case 5:
                authorsDAO.deleteAllAuthorsWithoutBooks();
            }
    }

    private void coordinateShowingAuthorsDetails() {
        view.printAuthors(authorsDAO.getAllAuthors());
        int authorID = getExistingAuthorID();
        Author author = authorsDAO.getAuthorById(authorID);
        if (author!=null){
            view.print(String.format("All books by %s %s:", author.getFirstName(), author.getLastName()));
            view.printBooks(booksDAO.getBooksByAuthorID(authorID));
        } else {
            view.print("Sorry, we couldn't show this user's details!");
        }
    }

    private void coordinateAddingNewAuthor() {
        String firstName = inputManager.getStringInput("Please type first name of the author");
        String lastName = inputManager.getStringInput("Please type last name of the author");
        Author newAuthor = new Author(firstName, lastName);
        authorsDAO.insertAuthor(newAuthor);
    }

    private void coordinateModifyingAuthor() {
        view.printAuthors(authorsDAO.getAllAuthors());
        int authorID = getExistingAuthorID();
        String firstName = inputManager.getStringInput("Please type the new first name of the author");
        String lastName = inputManager.getStringInput("Please type the new last name of the author");
        Author author = new Author(authorID, firstName, lastName);
        authorsDAO.updateAuthor(author);
    }

    private int getExistingAuthorID() {
        int authorID = inputManager.getIntInput("Please type the ID of the author you choose. Pick from the list above");
        while (!authorsDAO.checkIfAuthorIdInDatabase(authorID)) {
            authorID = inputManager.getIntInput("Wrong ID. Please try again:");
        }
        return authorID;
    }
}

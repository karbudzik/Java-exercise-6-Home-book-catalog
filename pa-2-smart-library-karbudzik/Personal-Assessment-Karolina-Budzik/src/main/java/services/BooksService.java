package services;

import authors.AuthorsDAO;
import books.Book;
import books.BooksDAO;
import interactions.InputManager;
import interactions.View;
import publishers.PublishersDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooksService extends Service {
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final PublishersDAO publishersDAO;

    public BooksService(BooksDAO booksDAO, AuthorsDAO authorsDAO, PublishersDAO publishersDAO) {
        super();
        this.booksDAO = booksDAO;
        this.authorsDAO = authorsDAO;
        this.publishersDAO = publishersDAO;
    }

    @Override
    void initializeMenu() {
        menuOptions = new ArrayList<>(Arrays.asList("Show all books", "Add book", "Update book's details",
                "Update book's ISBN", "Delete book by ISBN", "Delete all books", "Go back to main menu"));
    }

    @Override
    void manageUserChoice(int choice) {
        switch (choice) {
            case 1:
                view.printBooks(booksDAO.getAllBooks());
                break;
            case 2:
                coordinateAddingNewBook();
                break;
            case 3:
                coordinateUpdatingExistingBook();
                break;
            case 4:
                coordinateUpdatingISBN();
                break;
            case 5:
                coordinateBookDeletingProcess();
                break;
            case 6:
                booksDAO.deleteAllBooks();
        }
    }

    private void coordinateAddingNewBook() {
        try {
            Book newBook = getNewBookFromUserDetails();
            booksDAO.insertBook(newBook);
        } catch (IllegalArgumentException e) {
            view.print(e.getMessage());
        }
    }

    private void coordinateUpdatingExistingBook() {
        try {
            view.printBooks(booksDAO.getAllBooks());
            long ISBN = getExistingISBN();
            Book bookToUpdate = getBookToUpdateFromUserDetails(ISBN);
            booksDAO.updateBook(bookToUpdate);
        } catch (IllegalArgumentException e) {
            view.print(e.getMessage());
        }
    }

    private void coordinateUpdatingISBN() {
        view.printBooks(booksDAO.getAllBooks());
        long ISBN = getExistingISBN();
        long newISBN = inputManager.getLongInput("Please type new ISBN");
        booksDAO.updateBookISBN(ISBN, newISBN);
    }

    private void coordinateBookDeletingProcess() {
        view.printBooks(booksDAO.getAllBooks());
        long ISBN = getExistingISBN();
        booksDAO.deleteBook(ISBN);
    }

    private Book getNewBookFromUserDetails() throws IllegalArgumentException {
        long ISBN = inputManager.getLongInput("Please type the book's ISBN code (only numbers, no additional characters):");
        int authorID = getExistingAuthorID();
        String title = inputManager.getStringInput("Please type the title of your book: ");
        String publisherID = getExistingPublisherID();
        int publicationYear = inputManager.getIntInput("Please type the book's publication year");
        float price = inputManager.getFloatInput("Please type the book's price. Use 0.00 format:");

        return new Book(ISBN, authorID, title, publisherID, publicationYear, price);
    }

    private Book getBookToUpdateFromUserDetails(long ISBN) throws IllegalArgumentException {
        int authorID = getExistingAuthorID();
        String title = inputManager.getStringInput("Please type the title of your book: ");
        String publisherID = getExistingPublisherID();
        int publicationYear = inputManager.getIntInput("Please type the book's publication year");
        float price = inputManager.getFloatInput("Please type the book's price. Use 0.00 format:");

        return new Book(ISBN, authorID, title, publisherID, publicationYear, price);
    }

    private long getExistingISBN() {
        long ISBN = inputManager.getLongInput("Please type the ISBN of the book you want to change. Choose from the list above");
        while (!booksDAO.checkIfISBNInDatabase(ISBN)) {
            ISBN = inputManager.getLongInput("There is no such ISBN. Please try again:");
        }
        return ISBN;
    }

    private int getExistingAuthorID() throws IllegalArgumentException {
        view.printAuthors(authorsDAO.getAllAuthors());
        int authorID = inputManager.getIntInput("Please type the id of the author from the table above. " +
                "\nNOTE: Author needs to be in our database to add his/her book to the library" +
                "\nNOTE: If you don't see your author in the table above, go to main menu and add new author.");
        if (!authorsDAO.checkIfAuthorIdInDatabase(authorID)) {
            authorID = inputManager.getIntInput("Wrong ID. Please try again:");
            if (!authorsDAO.checkIfAuthorIdInDatabase(authorID)) {
                throw new IllegalArgumentException("This user ID don't exist in your database. Make sure to write correct ID or to add book's author first.");
            }
        }
        return authorID;
    }

    private String getExistingPublisherID() throws IllegalArgumentException {
        view.printPublishers(publishersDAO.getAllPublishers());
        String publisherID = inputManager.getStringInput("Please type the id of the publisher from the list above." +
                "\nNOTE: Publisher needs to be in our database to add their book to the library" +
                "\nNOTE: If you don't see your publisher on the list above, go to main menu and add new publisher");
        if (!publishersDAO.checkIfIDInDatabase(publisherID)) {
            publisherID = inputManager.getStringInput("Wrong ID. Please try again:");
            if (!publishersDAO.checkIfIDInDatabase(publisherID)) {
                throw new IllegalArgumentException("This publisher ID don't exist in your database. Make sure to write correct ID or to add book's publisher first.");
            }
        }
        return publisherID;
    }
}

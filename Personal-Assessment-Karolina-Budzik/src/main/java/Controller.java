import authors.AuthorsDAO;
import authors.AuthorsJDBCDAO;
import books.BooksDAO;
import books.BooksJDBCDAO;
import interactions.InputManager;
import publishers.PublishersDAO;
import publishers.PublishersJDBCDAO;
import services.AuthorsService;
import services.BooksService;
import services.Service;
import services.PublishersService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private final Service authorsService;
    private final Service booksService;
    private final Service publishersService;
    private final InputManager inputManager;
    private final List<String> menuOptions = new ArrayList<>(Arrays.asList("Books", "Authors", "Publishers", "Quit"));

    public Controller() {
        BooksDAO booksDAO = new BooksJDBCDAO();
        AuthorsDAO authorsDAO = new AuthorsJDBCDAO();
        PublishersDAO publishersDAO = new PublishersJDBCDAO();
        authorsService = new AuthorsService(booksDAO, authorsDAO);
        booksService = new BooksService(booksDAO, authorsDAO, publishersDAO);
        publishersService = new PublishersService(booksDAO, publishersDAO);
        inputManager = new InputManager();
    }

    public void run() {
        int choice = inputManager.askForMenuOption(menuOptions, "Library main menu");
        manageUserChoice(choice);
        while (choice != menuOptions.size()) {
            choice = inputManager.askForMenuOption(menuOptions, "Library main menu");
            manageUserChoice(choice);
        }
    }

    void manageUserChoice(int choice) {
        switch(choice) {
            case 1:
                booksService.run();
                break;
            case 2:
                authorsService.run();
                break;
            case 3:
                publishersService.run();
                break;
        }
    }
}

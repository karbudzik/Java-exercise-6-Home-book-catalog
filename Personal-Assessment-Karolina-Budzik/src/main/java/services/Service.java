package services;

import interactions.InputManager;
import interactions.View;

import java.util.List;

public abstract class Service {
    protected final InputManager inputManager;
    protected final View view;
    protected List<String> menuOptions;

    public Service() {
        inputManager = new InputManager();
        view = new View();
        initializeMenu();
    }

    public void run() {
        int choice = inputManager.askForMenuOption(menuOptions, "Authors menu");
        manageUserChoice(choice);
        while (choice != menuOptions.size()) {
            choice = inputManager.askForMenuOption(menuOptions, "Authors menu");
            manageUserChoice(choice);
        }
    }

    abstract void initializeMenu();

    abstract void manageUserChoice(int choice);
}

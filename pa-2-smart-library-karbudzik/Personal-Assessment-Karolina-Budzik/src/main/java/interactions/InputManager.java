package interactions;

import java.util.List;
import java.util.Scanner;

public class InputManager {
    private final View view;

    public InputManager() {
        view = new View();
    }

    public String getStringInput(String message) {
        view.print("\n" + message);
        Scanner scannerFromUser = new Scanner(System.in);

        return scannerFromUser.nextLine();
    }

    public float getFloatInput(String message) {
        view.print("\n" + message);
        Scanner scannerFromUser = new Scanner(System.in);

        while(!scannerFromUser.hasNextFloat()){
            view.print("\nWrong input! Please insert the correct number");
            scannerFromUser.next();
        }
        return scannerFromUser.nextFloat();
    }

    public long getLongInput(String message) {
        view.print("\n" + message);
        Scanner scannerFromUser = new Scanner(System.in);

        while(!scannerFromUser.hasNextLong()){
            view.print("\nWrong input! Please insert the correct number");
            scannerFromUser.next();
        }
        return scannerFromUser.nextLong();
    }

    public int getIntInput(String message) {
        view.print("\n" + message);
        Scanner scannerFromUser = new Scanner(System.in);

        while (!scannerFromUser.hasNextInt()) {
            view.print("\nWrong input! Please insert the correct number");
            scannerFromUser.next();
        }
        return scannerFromUser.nextInt();
    }

    public int askForMenuOption(List<String> menuOptions, String menuTitle) {
        view.printMenu(menuOptions, menuTitle);
        int statNumber = getIntInput("What do you choose? Type the number:");

        while (statNumber < 1 || statNumber > menuOptions.size()) {
            statNumber = getIntInput(String.format("\nWrong input! type the number between 1 and %d:",
                    menuOptions.size()));
        }
        return statNumber;
    }

}

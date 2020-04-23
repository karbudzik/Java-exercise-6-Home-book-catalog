import connection.DatabaseContentLoader;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseContentLoader contentLoader = new DatabaseContentLoader();
            contentLoader.fillDatabase();
        } catch (RuntimeException e) {
            System.out.println("You don't have database in your computer. " +
                               "Create db named 'smart_library' before running the program");
        }

        Controller controller = new Controller();
        controller.run();
    }
}

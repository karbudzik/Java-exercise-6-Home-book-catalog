package interactions;

import authors.Author;
import books.Book;
import publishers.Publisher;

import java.util.List;

public class View {
    public void print(String message) {
        System.out.println(message);
    }

    public void printMenu(List<String> menuItems, String title) {
        int index = 1;
        System.out.println(String.format("\n%s:", title.toUpperCase()));
        for (String item : menuItems) {
            System.out.println(String.format("%d. %s", index, item));
            index ++;
        }
    }

    public void printAuthors(List<Author> authors) {
        int count = 1;
        System.out.println("\nALL AVAILABLE AUTHORS");
        for (Author author : authors) {
            System.out.println(String.format("%d. %s", count, author.toString()));
            count++;
        }
    }

    public void printBooks(List<Book> library) {
        int count = 1;
        System.out.println("\nALL AVAILABLE BOOKS");
        for (Book book : library) {
            System.out.println(String.format("%d. %s", count, book.toString()));
            count++;
        }
    }

    public void printPublishers(List<Publisher> publishers) {
        int count = 1;
        System.out.println("\nALL AVAILABLE PUBLISHERS");
        for (Publisher publisher : publishers) {
            System.out.println(String.format("%d. %s", count, publisher.toString()));
            count++;
        }
    }
}

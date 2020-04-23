package books;

public class Book {
    private long ISBN;
    private int authorID;
    private String title;
    private String publisherID;
    private int publicationYear;
    private float price;

    public Book(long ISBN, int authorID, String title, String publisherID, int publicationYear, float price) {
        this.ISBN = ISBN;
        this.authorID = authorID;
        this.title = title;
        this.publisherID = publisherID;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public long getISBN() {
        return ISBN;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public float getPrice() {
        return price;
    }

    public String getPublisherID() {
        return publisherID;
    }

    @Override
    public String toString() {
        return  "ISBN='" + ISBN + '\'' +
                ", authorID=" + authorID +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price;
    }
}

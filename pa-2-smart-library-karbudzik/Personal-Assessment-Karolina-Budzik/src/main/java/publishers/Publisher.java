package publishers;

public class Publisher {
    private String ID;
    private String name;

    public Publisher(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID='" + ID + '\'' +
                ", name='" + name + '\'';
    }
}

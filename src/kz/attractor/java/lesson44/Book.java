package kz.attractor.java.lesson44;

public class Book {
    private int id;
    private String name;
    private String about;
    private String status;
    private String imagePath;
    private String description;



    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }
}

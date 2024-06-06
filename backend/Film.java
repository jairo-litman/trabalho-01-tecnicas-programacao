package backend;

public class Film {
    public int id = -1;
    public String title;
    public int year;
    public String genre;

    public Film(int id, String title, int year, String genre) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    public Film(String title, int year, String genre) {
        this.title = title;
        this.year = year;
        this.genre = genre;
    }
}

import javax.swing.SwingUtilities;

import backend.*;
import frontend.*;

public class Main {
    public static void main(String[] args) {
        // Set up the DBManager instance
        DBManager dbManager = new DBManager();
        Film film = new Film("The Shawshank Redemption", 1994, "Drama");
        dbManager.addFilm(film);
        film = new Film("The Godfather", 1972, "Crime");
        dbManager.addFilm(film);

        for (Film f : dbManager.getFilms()) {
            System.out.println(f.title);
        }

        dbManager.deleteFilm(1);

        for (Film f : dbManager.getFilms()) {
            System.out.println(f.title);
        }

        film = dbManager.getFilm(2);
        film.title = "The Godfather: Part II";
        dbManager.updateFilm(film);

        for (Film f : dbManager.getFilms()) {
            System.out.println(f.title);
        }

        // Create and show the XilftenMenu
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new XilftenMenu(dbManager);
            }
        });

        dbManager.close();
    }
}
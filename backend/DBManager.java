package backend;

import java.sql.*;

public class DBManager {
    private Connection conn = null;

    public DBManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            conn = DriverManager.getConnection("jdbc:hsqldb:file:database/db", "SA", "");
            conn.createStatement().executeUpdate("SET DATABASE SQL SYNTAX PGS TRUE");

            this.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFilm(Film film) {
        try {
            Film existingFilm = this.getFilm(film.title);
            if (existingFilm != null) {
                this.updateFilm(film);
                return;
            }

            PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO films (name, year, genre) VALUES (?, ?, ?)");
            stmt.setString(1, film.title);
            stmt.setInt(2, film.year);
            stmt.setString(3, film.genre);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFilm(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM films WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Film[] getFilms() {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM films");
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            Film[] films = new Film[size];
            int i = 0;
            while (rs.next()) {
                films[i] = new Film(rs.getInt("id"), rs.getString("name"), rs.getInt("year"),
                        rs.getString("genre"));
                i++;
            }
            rs.close();
            stmt.close();
            return films;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Film getFilm(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM films WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Film film = new Film(rs.getInt("id"), rs.getString("name"), rs.getInt("year"),
                    rs.getString("genre"));
            rs.close();
            stmt.close();
            return film;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Film getFilm(String title) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM films WHERE name = ?");
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                rs.close();
                stmt.close();
                return null;
            }
            Film film = new Film(rs.getInt("id"), rs.getString("name"), rs.getInt("year"),
                    rs.getString("genre"));
            rs.close();
            stmt.close();
            return film;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateFilm(Film film) {
        try {
            PreparedStatement stmt = conn
                    .prepareStatement("UPDATE films SET name = ?, year = ?, genre = ? WHERE id = ?");
            stmt.setString(1, film.title);
            stmt.setInt(2, film.year);
            stmt.setString(3, film.genre);
            stmt.setInt(4, film.id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS films " +
                    "(id SERIAL PRIMARY KEY," +
                    "name TEXT NOT NULL, " +
                    "year INT NOT NULL, " +
                    "genre TEXT NOT NULL)");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

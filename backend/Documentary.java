package backend;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a documentary.
 *
 * <p>
 * Extends the {@link Film} class to change the name of the database table that
 * stores the documentaries.
 */
public class Documentary extends Film {
    /**
     * The name of the database table that stores the documentaries.
     */
    public static final String TABLE_NAME = "documentaries";

    /**
     * Creates a new documentary with the given title, year, genre and duration.
     * 
     * <p>
     * This is the constructor that is expected to be used by the user of this
     * package when creating a new documentary.
     * 
     * @param title    the title of the documentary
     * @param year     the year the documentary was released
     * @param genre    the genre of the documentary
     * @param duration the duration of the documentary in minutes
     */
    public Documentary(String title, int year, String genre, int duration) {
        super(title, year, genre, duration);
    }

    /**
     * Creates a new documentary from the given {@link ResultSet}.
     * 
     * <p>
     * This is a protected constructor, it is not expected to be used by the user of
     * this package.
     * 
     * @param rs the {@link ResultSet} to create the documentary from
     * @throws SQLException if a database error occurs
     */
    protected Documentary(ResultSet rs) throws SQLException {
        super(rs);
    }

    /**
     * Returns the sql for creating the films table.
     */
    public static String getSQLCreateTable() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (id SERIAL PRIMARY KEY, title TEXT NOT NULL, year INT NOT NULL, genre TEXT NOT NULL, duration INT NOT NULL);",
                TABLE_NAME);
    }

    /**
     * Returns the sql table name for films.
     */
    @Override
    public String getSQLTableName() {
        return TABLE_NAME;
    }
}

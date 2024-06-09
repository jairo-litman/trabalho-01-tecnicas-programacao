package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents a film.
 *
 * <p>
 * Extends the {@link Media} class to include a duration field.
 */
public class Film extends Media {
    /**
     * The duration of the film in minutes.
     */
    protected int duration;

    /**
     * The name of the database table that stores the films.
     */
    public static final String TABLE_NAME = "films";

    /**
     * Creates a new film with the given title, year, genre, and duration.
     * 
     * <p>
     * This is the constructor that is expected to be used by the user of this
     * package when creating a new film.
     * 
     * @param title    the title of the film
     * @param year     the year the film was released
     * @param genre    the genre of the film
     * @param duration the duration of the film in minutes
     */
    public Film(String title, int year, String genre, int duration) {
        super(title, year, genre);
        this.duration = duration;
    }

    /**
     * Creates a new film from the given {@link ResultSet}.
     * 
     * <p>
     * This is a protected constructor, it is not expected to be used by the
     * user of this package.
     * 
     * @param rs the {@link ResultSet} to create the film from
     * @throws SQLException if a database error occurs
     */
    protected Film(ResultSet rs) throws SQLException {
        super(rs);
        this.duration = rs.getInt("duration");
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns the sql table name for films.
     */
    @Override
    public String getSQLTableName() {
        return TABLE_NAME;
    }

    /**
     * Returns the names of the columns in the database table that store the film's
     * data.
     * 
     * @param includeID whether to include the ID column
     * @return the names of the columns in the database table that store the film's
     *         data
     */
    @Override
    public List<String> getSQLColumns(boolean includeID) {
        List<String> columns = super.getSQLColumns(includeID);
        columns.add("duration");
        return columns;
    }

    /**
     * Returns the values of the film's data to be stored in the database.
     * 
     * @param includeID whether to include the ID value
     * @return the values of the film's data to be stored in the database
     */
    @Override
    public List<Object> getSQLValues(boolean includeID) {
        List<Object> values = super.getSQLValues(includeID);
        values.add(this.duration);
        return values;
    }
}

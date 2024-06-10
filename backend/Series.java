package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents a series.
 * 
 * <p>
 * Extends the {@link Media} class to include the number of seasons.
 */
public class Series extends Media {
    /**
     * The number of seasons in the series.
     */
    protected int seasons;

    /**
     * The name of the database table that stores the series.
     */
    public static final String TABLE_NAME = "series";

    /**
     * Creates a new series with the given title, year, genre and number of seasons.
     * 
     * <p>
     * This is the constructor that is expected to be used by the user of this
     * package when creating a new series.
     * 
     * @param title   the title of the series
     * @param year    the year the series was released
     * @param genre   the genre of the series
     * @param seasons the number of seasons in the series
     */
    public Series(String title, int year, String genre, int seasons) {
        super(title, year, genre);
        this.seasons = seasons;
    }

    /**
     * Creates a new series from the given {@link ResultSet}.
     * 
     * <p>
     * This is a protected constructor, it is not expected to be used by the user of
     * this package.
     * 
     * @param rs the {@link ResultSet} to create the series from
     * @throws SQLException if a database error occurs
     */
    protected Series(ResultSet rs) throws SQLException {
        super(rs);
        this.seasons = rs.getInt("seasons");
    }

    public int getSeasons() {
        return this.seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    /**
     * Returns the sql for creating the films table.
     */
    public static String getSQLCreateTable() {
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s (id SERIAL PRIMARY KEY, title TEXT NOT NULL, year INT NOT NULL, genre TEXT NOT NULL, seasons INT NOT NULL);",
                TABLE_NAME);
    }

    /**
     * Returns the sql table name for films.
     */
    @Override
    public String getSQLTableName() {
        return TABLE_NAME;
    }

    /**
     * Returns the columns of the database table that stores the series.
     * 
     * @param includeID whether to include the ID column
     * @return the columns of the database table that store the series
     */
    @Override
    public List<String> getSQLColumns(boolean includeID) {
        List<String> columns = super.getSQLColumns(includeID);
        columns.add("seasons");
        return columns;
    }

    /**
     * Returns the values of the series to be stored in the database.
     * 
     * @param includeID whether to include the ID value
     * @return the values of the series to be stored in the database
     */
    @Override
    public List<Object> getSQLValues(boolean includeID) {
        List<Object> values = super.getSQLValues(includeID);
        values.add(this.seasons);
        return values;
    }
}

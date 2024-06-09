package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class representing a media item such as a {@link Film},
 * {@link Series}, or {@link Documentary}.
 * 
 * <p>
 * Provides methods for saving, updating, and deleting media items in a
 * database.
 * 
 * <p>
 * Subclasses must implement the {@link #getSQLTableName()} method to return the
 * name of the database table that stores the {@link Media} items.
 * 
 * <p>
 * Subclasses may override the {@link #getSQLColumns(boolean)} and
 * {@link #getSQLValues(boolean)} methods to customize the columns and values
 * stored in the database.
 * 
 * <p>
 * Subclasses may also override the {@link #saveToDB(MediaDAO)},
 * {@link #updateInDB(MediaDAO)}, and {@link #deleteFromDB(MediaDAO)} methods to
 * customize how the media items are saved, updated, and deleted in the
 * database.
 */
public abstract class Media {
    /**
     * The ID of the media item.
     */
    protected int id = -1;
    /**
     * The title of the media item.
     */
    protected String title;
    /**
     * The year the media item was released.
     */
    protected int year;
    /**
     * The genre of the media item.
     */
    protected String genre;

    /**
     * Creates a new media item with the specified title, year, and genre.
     * 
     * <p>
     * This constructor is protected because it should only be called by
     * subclasses.
     * 
     * @param title the title of the media item
     * @param year  the year the media item was released
     * @param genre the genre of the media item
     */
    protected Media(String title, int year, String genre) {
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    /**
     * Creates a new media item from the given {@link ResultSet}.
     * 
     * <p>
     * This constructor is protected because it should only be called by
     * subclasses.
     * 
     * @param rs the {@link ResultSet} to create the media item from
     * @throws SQLException if a database error occurs
     */
    protected Media(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.title = rs.getString("title");
        this.year = rs.getInt("year");
        this.genre = rs.getString("genre");
    }

    /**
     * Returns a string representation of the media item.
     */
    public String toString() {
        return String.format("%d: %s (%d)", this.id, this.title, this.year);
    }

    /**
     * Compares the id of this media item to the id of the other media item.
     */
    public boolean equals(Media other) {
        return this.id == other.id;
    }

    /**
     * Returns the name of the database table that stores the media items.
     */
    public abstract String getSQLTableName();

    /**
     * Returns the columns of the media item's data to be stored in the database.
     * 
     * @param includeID whether to include the ID column
     * @return the columns of the media item's data to be stored in the database
     */
    public List<String> getSQLColumns(boolean includeID) {
        ArrayList<String> columns = new ArrayList<String>(Arrays.asList("title", "year", "genre"));
        if (includeID) {
            columns.add(0, "id");
        }
        return columns;
    }

    /**
     * Returns the values of the media item's data to be stored in the database.
     * 
     * @param includeID whether to include the ID value
     * @return the values of the media item's data to be stored in the database
     */
    public List<Object> getSQLValues(boolean includeID) {
        ArrayList<Object> values = new ArrayList<Object>();
        if (includeID) {
            values.add(this.id);
        }
        values.add(this.title);
        values.add(this.year);
        values.add(this.genre);
        return values;
    }

    /**
     * Saves the media item to the database using the specified data access object.
     * 
     * @param dao the data access object to save the media item with
     * @throws SQLException if a database error occurs
     */
    public void saveToDB(MediaDAO dao) throws SQLException {
        dao.save(this);
    }

    /**
     * Updates the media item in the database using the specified data access
     * object.
     * 
     * @param dao the data access object to update the media item with
     * @throws SQLException if a database error occurs
     */
    public void updateInDB(MediaDAO dao) throws SQLException {
        dao.update(this);
    }

    /**
     * Deletes the media item from the database using the specified data access
     * object.
     * 
     * @param dao the data access object to delete the media item with
     * @throws SQLException if a database error occurs
     */
    public void deleteFromDB(MediaDAO dao) throws SQLException {
        dao.delete(this);
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getYear() {
        return this.year;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

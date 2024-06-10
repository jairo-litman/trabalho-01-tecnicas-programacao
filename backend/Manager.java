package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Manager class to handle the database connection and operations.
 * 
 * <p>
 * This class implements the MediaDAO interface and provides methods to save,
 * update, delete, and get media objects from the database.
 * 
 * <p>
 * This class is a singleton and should be accessed through the getInstance
 * method.
 * 
 * <p>
 * The database URL is hardcoded and the SQL syntax is set to
 * PostgreSQL.
 * 
 * <p>
 * The database will be created in the database folder in the project root if it
 * doesn't exist. The database will contain three tables: films, series, and
 * documentaries that will also be created if they don't exist.
 * 
 * @throws SQLException if an error occurs while accessing the database
 * 
 * @see MediaDAO
 * @see Media
 */
public class Manager implements MediaDAO {
    /**
     * Singleton instance of the Manager class
     */
    private static Manager instance = null;

    /**
     * URL to the database
     */
    private static final String URL = "jdbc:hsqldb:file:database/db";

    /**
     * Private constructor to create the Manager class. Will create the tables if
     * they don't exist and set the SQL syntax to PostgreSQL
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    private Manager() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET DATABASE SQL SYNTAX PGS TRUE");

            stmt.executeUpdate(Film.getSQLCreateTable());
            stmt.executeUpdate(Series.getSQLCreateTable());
            stmt.executeUpdate(Documentary.getSQLCreateTable());
        }
    }

    /**
     * Get the singleton instance of the Manager class, creating it if it doesn't
     * exist. Should be treated as the entry point to the Manager class
     * 
     * @return the singleton instance of the Manager class
     * @throws SQLException if an error occurs while accessing the database
     */
    public static synchronized Manager getInstance() throws SQLException {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    /**
     * Save the provided media object to the database, the ID of the media object
     * will be handled by the database
     * 
     * @param media the media object to save
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public void save(Media media) throws SQLException {
        String sql = "INSERT INTO %s (%s) VALUES (%s)";

        List<Object> sqlValues = media.getSQLValues(false);

        String table = media.getSQLTableName();
        String cols = String.join(", ", media.getSQLColumns(false));
        String values = String.join(", ", Collections.nCopies(sqlValues.size(), "?"));

        sql = String.format(sql, table, cols, values);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            int idx = 1;
            for (Object o : sqlValues) {
                stmt.setObject(idx++, o);
            }

            stmt.executeUpdate();
        }
    }

    /**
     * Update the media object in the database to match the media object provided.
     * Should only update one object
     * 
     * @param media the media object to update, must have a valid ID
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public void update(Media media) throws SQLException {
        if (media.id == -1) {
            throw new IllegalArgumentException("Media object has no ID");
        }

        String sql = "UPDATE %s SET %s WHERE id = ?";

        String table = media.getSQLTableName();
        String cols = String.join(" = ?, ", media.getSQLColumns(false)) + " = ?";
        sql = String.format(sql, table, cols);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            int idx = 1;
            for (Object o : media.getSQLValues(false)) {
                stmt.setObject(idx++, o);
            }

            stmt.setInt(idx, media.id);

            stmt.executeUpdate();
        }
    }

    /**
     * Delete all media objects from the database that match the media object.
     * Should only delete one object
     * 
     * @param media the media object to delete, must have a valid ID
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public void delete(Media media) throws SQLException {
        if (media.id == -1) {
            throw new IllegalArgumentException("Media object has no ID");
        }

        String sql = "DELETE FROM %s WHERE id = ?";

        String table = media.getSQLTableName();
        sql = String.format(sql, table);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, media.id);

            stmt.executeUpdate();
        }
    }

    /**
     * Get a list of all media objects from the database in the specified table with
     * the specified ID, should return a list with one object
     * 
     * @param table the table name
     * @param id    the ID to search for
     * 
     * @return a list of media objects, or an empty list if no objects are found
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public List<Media> get(String table, int id) throws SQLException {
        String sql = "SELECT * FROM %s WHERE id = ?";
        sql = String.format(sql, table);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            return getList(rs, table);
        }
    }

    /**
     * Get a list of all media objects from the database in the specified table with
     * the exact specified title in a case-insensitive manner
     * 
     * @param table the table name
     * @param title the title to search for
     * 
     * @return a list of media objects, or an empty list if no objects are found
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public List<Media> get(String table, String title) throws SQLException {
        String sql = "SELECT * FROM %s WHERE LOWER(title) = LOWER(?)";
        sql = String.format(sql, table);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, title);

            ResultSet rs = stmt.executeQuery();

            return getList(rs, table);
        }
    }

    /**
     * Get a list of all media objects from the database in the specified table
     * 
     * @param table the table name
     * 
     * @return a list of media objects, or an empty list if no objects are found
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public List<Media> get(String table) throws SQLException {
        String sql = "SELECT * FROM %s";
        sql = String.format(sql, table);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);

            return getList(rs, table);
        }
    }

    /**
     * Get a list of media objects from the database in the specified table that
     * have a title containing the specified string in a case-insensitive manner
     * 
     * @param table the table name
     * @param title the title to search for
     * 
     * @return a list of media objects, or an empty list if no objects are found
     * 
     * @throws SQLException if an error occurs while accessing the database
     */
    @Override
    public List<Media> getFuzzy(String table, String title) throws SQLException {
        String sql = "SELECT * FROM %s WHERE LOWER(title) LIKE LOWER(?)";
        sql = String.format(sql, table);

        try (Connection conn = DriverManager.getConnection(URL, "SA", "")) {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, "%" + title + "%");

            ResultSet rs = stmt.executeQuery();

            return getList(rs, table);
        }
    }

    /**
     * Helper method to create a list of media objects from a result set
     * 
     * @param rs    the result set
     * @param table the table name
     * 
     * @return a list of media objects, or an empty list if no objects are found
     * 
     * @throws SQLException if an error occurs while accessing the result set
     */
    private static List<Media> getList(ResultSet rs, String table) throws SQLException {
        if (rs.next()) {
            MediaListFactory factory = MediaFactoryProvider.getFactory(table);
            if (factory == null) {
                throw new IllegalArgumentException("Invalid table name");
            }
            return factory.create(rs);
        }

        return Collections.emptyList();
    }
}

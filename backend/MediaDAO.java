package backend;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents a data access object for {@link Media}.
 * 
 * <p>
 * This interface provides methods to interact with a database to retrieve,
 * save, update, and delete {@link Media} items.
 * 
 * @see Manager
 */
public interface MediaDAO {
    public List<Media> get(String table, int id) throws SQLException;

    public List<Media> get(String table, String title) throws SQLException;

    public List<Media> get(String table) throws SQLException;

    public List<Media> getFuzzy(String table, String title) throws SQLException;

    public void save(Media media) throws SQLException;

    public void update(Media media) throws SQLException;

    public void delete(Media media) throws SQLException;
}
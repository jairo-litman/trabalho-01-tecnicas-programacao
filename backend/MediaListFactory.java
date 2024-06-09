package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Functional interface for creating a new object from a {@link ResultSet}.
 * 
 * @param <T> the type of object to create
 * 
 * @see MediaListFactory
 */
@FunctionalInterface
interface ConstructorFunction<T> {
    T apply(ResultSet rs) throws SQLException;
}

/**
 * Represents a factory for creating lists of {@link Media} objects.
 * 
 * <p>
 * This class is responsible for creating lists of {@link Media} objects from a
 * {@link ResultSet}.
 * 
 * @see Film
 * @see Series
 * @see Documentary
 */
abstract class MediaListFactory {
    /**
     * Creates a list of {@link Media} objects from the given {@link ResultSet}.
     * 
     * @param rs the {@link ResultSet} to create the {@link Media} objects from
     * @return a list of media objects
     * @throws SQLException if a database error occurs
     */
    abstract List<Media> create(ResultSet rs) throws SQLException;

    /**
     * Creates a list of {@link Media} objects from the given {@link ResultSet}
     * using the
     * specified constructor.
     * 
     * @param rs          the {@link ResultSet} to create the {@link Media} objects
     *                    from
     * @param constructor the constructor to use to create the {@link Media} objects
     * @return a list of {@link Media} objects
     * @throws SQLException if a database error occurs
     */
    protected List<Media> create(ResultSet rs, ConstructorFunction<Media> constructor) throws SQLException {
        // Get the size of the result set
        int size = 0;
        if (rs.last()) {
            size = rs.getRow();
            rs.beforeFirst();
        }

        // If the result set is empty, return an empty list
        if (size == 0) {
            return List.of();
        }

        // Create a list of media objects using the specified constructor
        List<Media> media = new java.util.ArrayList<Media>(size);
        while (rs.next()) {
            media.add(constructor.apply(rs));
        }

        return media;
    }
}

/**
 * Provides a factory for creating lists of {@link Media} objects.
 * 
 * @see MediaListFactory
 */
class MediaFactoryProvider {
    /**
     * The map of table names to {@link Media} factories.
     */
    private static final Map<String, MediaListFactory> factories = new HashMap<>();

    // Initialize the map of table names to media factories
    static {
        factories.put("films", new FilmListFactory());
        factories.put("series", new SeriesListFactory());
        factories.put("documentaries", new DocumentaryListFactory());
    }

    /**
     * Returns the {@link Media} factory for the specified table name.
     * 
     * @param tableName the name of the table
     * @return the {@link Media} factory for the specified table name
     */
    public static MediaListFactory getFactory(String tableName) {
        return factories.get(tableName);
    }
}

/**
 * Provides a factory for creating lists of {@link Film} objects.
 * 
 * <p>
 * This class is responsible for creating lists of {@link Film} objects from a
 * {@link ResultSet}.
 * 
 * <p>
 * Extends the {@link MediaListFactory} class to create lists of {@link Film}
 * objects.
 * 
 * @see Film
 * @see MediaListFactory
 * @see MediaFactoryProvider
 */
class FilmListFactory extends MediaListFactory {
    @Override
    public List<Media> create(ResultSet rs) throws SQLException {
        return super.create(rs, (ConstructorFunction<Media>) Film::new);
    }
}

/**
 * Provides a factory for creating lists of {@link Series} objects.
 * 
 * <p>
 * This class is responsible for creating lists of {@link Series} objects from a
 * {@link ResultSet}.
 * 
 * <p>
 * Extends the {@link MediaListFactory} class to create lists of {@link Series}
 * objects.
 * 
 * @see Series
 * @see MediaListFactory
 * @see MediaFactoryProvider
 */
class SeriesListFactory extends MediaListFactory {
    @Override
    public List<Media> create(ResultSet rs) throws SQLException {
        return super.create(rs, (ConstructorFunction<Media>) Series::new);
    }
}

/**
 * Provides a factory for creating lists of {@link Documentary} objects.
 * 
 * <p>
 * This class is responsible for creating lists of {@link Documentary} objects
 * from a {@link ResultSet}.
 * 
 * <p>
 * Extends the {@link MediaListFactory} class to create lists of
 * {@link Documentary} objects.
 * 
 * @see Documentary
 * @see MediaListFactory
 * @see MediaFactoryProvider
 */
class DocumentaryListFactory extends MediaListFactory {
    @Override
    public List<Media> create(ResultSet rs) throws SQLException {
        return super.create(rs, (ConstructorFunction<Media>) Documentary::new);
    }
}

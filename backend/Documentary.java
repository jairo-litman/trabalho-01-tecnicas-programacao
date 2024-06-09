package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents a documentary.
 *
 * <p>
 * Extends the {@link Film} class to include a topic field.
 */
public class Documentary extends Film {
    /**
     * The topic of the documentary.
     */
    protected String topic;

    /**
     * The name of the database table that stores the documentaries.
     */
    public static final String TABLE_NAME = "documentaries";

    /**
     * Creates a new documentary with the given title, year, genre, duration, and
     * topic.
     * 
     * <p>
     * This is the constructor that is expected to be used by the user of this
     * package when creating a new documentary.
     * 
     * @param title    the title of the documentary
     * @param year     the year the documentary was released
     * @param genre    the genre of the documentary
     * @param duration the duration of the documentary in minutes
     * @param topic    the topic of the documentary
     */
    public Documentary(String title, int year, String genre, int duration, String topic) {
        super(title, year, genre, duration);
        this.topic = topic;
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
        this.topic = rs.getString("topic");
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Returns the sql table name for films.
     */
    @Override
    public String getSQLTableName() {
        return TABLE_NAME;
    }

    /**
     * Returns the names of the columns in the database table that store the
     * documentary's data.
     * 
     * @param includeID whether to include the ID column
     * @return the names of the columns in the database table that store the
     *         documentary's data
     */
    @Override
    public List<String> getSQLColumns(boolean includeID) {
        List<String> columns = super.getSQLColumns(includeID);
        columns.add("topic");
        return columns;
    }

    /**
     * Returns the values of the documentary's data to be stored in the database.
     * 
     * @param includeID whether to include the ID value
     * @return the values of the documentary's data to be stored in the database
     */
    @Override
    public List<Object> getSQLValues(boolean includeID) {
        List<Object> values = super.getSQLValues(includeID);
        values.add(this.topic);
        return values;
    }
}

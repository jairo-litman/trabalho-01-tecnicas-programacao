package backend;

import java.sql.*;

public class DBManager {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public DBManager() {
        try {
            Class.forName("org.hsql.jdbcDriver");

            conn = DriverManager.getConnection("jdbc:hsqldb:file:database/db", "sa", "");
            stmt = conn.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS films " +
                    "(id INTEGER, name VARCHAR(255), year INTEGER, duration INTEGER, genre VARCHAR(255), PRIMARY KEY (id))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

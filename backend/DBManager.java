package backend;

import java.sql.*;

public class DBManager {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public DBManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            conn = DriverManager.getConnection("jdbc:hsqldb:file:database/db", "SA", "");
            stmt = conn.createStatement();

            stmt.executeUpdate("SET DATABASE SQL SYNTAX PGS TRUE");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS films " +
                    "(id SERIAL PRIMARY KEY," +
                    "name TEXT NOT NULL, " +
                    "year INT NOT NULL, " +
                    "duration INT NOT NULL, " +
                    "genre TEXT NOT NULL)");

            PreparedStatement ps = conn.prepareStatement("INSERT INTO films VALUES (DEFAULT, ?, ?, ?, ?)");
            ps.setString(1, "The Shawshank Redemption");
            ps.setInt(2, 1994);
            ps.setInt(3, 142);
            ps.setString(4, "Drama");
            ps.executeUpdate();

            rs = stmt.executeQuery("SELECT * FROM films");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Year: " + rs.getInt("year")
                                + ", Duration: " + rs.getInt("duration") + ", Genre: " + rs.getString("genre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

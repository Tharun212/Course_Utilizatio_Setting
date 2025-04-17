/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package narst_course_utilisation_setting;

/**
 *
 * @author tharu
 */
import java.sql.*;

public class SQLDB {
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rset = null;
    public static void connect(String dbpath) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void execute(String query) {
        try {
            rset = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
    public static void update(String query) {
        try {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package banking;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Operations {
	//Connection:
	public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:card.s3db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

//            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
	//-------------------------------------------------------------------------
	public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
	//-------------------------------------------------------------------------
	public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:card.s3db.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number TEXT NOT NULL, " +
                "pin TEXT NOT NULL, " +
                "balance INTEGER DEFAULT 0)";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
//            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	//-------------------------------------------------------------------------------
	

}

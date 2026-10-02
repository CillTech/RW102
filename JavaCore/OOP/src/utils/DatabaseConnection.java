package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConnection.class.getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Xin lỗi, không tìm thấy file database.properties");
            } else {
                properties.load(input);
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package vehicleverificationsystem.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vehicle_verification";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234"; // Replace with your MySQL root password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

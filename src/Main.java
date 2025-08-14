import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        String url = "jdbc:mysql://localhost:3306/expense";  // Root connection, no database specified
        String user = "root";
        String password = "Virat@18";
        String databaseName = "expense";
        Connection connection = null;

        try {
            // Establish a connection to the database server (no specific database selected)
                connection = DriverManager.getConnection(url, user, password);
                Controller controller=new Controller();
                controller.displayMenu(connection);
        } catch (SQLException e) {
                e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    }




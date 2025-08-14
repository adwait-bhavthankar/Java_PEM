import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static java.sql.DriverManager.getConnection;

public class User {


    Integer userId;
    String userName;
    String passWord;


    private Repository repository;

    Budget budget;

    boolean loggedIn = false;
    String url = "jdbc:mysql://localhost:3306/expense";  // Root connection, no database specified
    String user = "root";
    String password = "Reznov@18";

    public void signUp(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void createRepository() {
        if (repository == null) {
            repository = new Repository();
            repository.setUser(this);
            System.out.println("Repository Created For User ! ");
            // Associate the user with the repository
        } else {
            System.out.println("User can have only one associated repository");
        }
    }

    public Repository getRepository() {
        return repository;
    }


    public void createBudget(long amount) {
        if (isLoggedIn()) {
            if (budget == null) {
                budget = new Budget();
                budget.setTotalBudget(amount);
                budget.setUser(this);
                System.out.println("Initial Budget : " + amount);

            } else {
                System.out.println("No Budget Is Initialized !");
            }
        } else {
            System.out.println("User Not Logged In !!!");
        }
    }

    public void saveToDatabase() {
        try (Connection connection = getConnection(url, user, password)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, passWord);

            statement.executeUpdate();
            setLoggedIn(true);
            getId(userName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean loginUser(String inputUsername, String inputPassword) {

        if (isLoggedIn()) {
            System.out.println("Already Logged In !");
            return false;

        } else {
            try (Connection connection = getConnection(url, user, password)) {
                String query = "SELECT * FROM users WHERE username = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, inputUsername);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // User found, check password
                    String storedPassword = resultSet.getString("password");
                    Integer id = resultSet.getInt("id");
                    setUserId(id);
                    if (inputPassword.equals(storedPassword)) {
                        signUp(inputUsername, inputPassword);
                        //  System.out.println(this.userId+" "+this.userName+" "+this.passWord);
                        System.out.println("User Logged In!");
                        signUp(inputUsername, inputPassword);
                        setLoggedIn(true);
                        getBudgetDB(inputUsername);
                        return true;

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Incorrect Credentials!");
            setLoggedIn(false);
            return false;
        }


    }

    // Load user details from the database based on username
    public void loadFromDatabase(String username) {
        try (Connection connection = getConnection(url, user, password)) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.userName = ((ResultSet) resultSet).getString("username");
                this.passWord = resultSet.getString("password");
                // Set other user details as needed

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update user details in the database
    public void updateInDatabase() {
        try (Connection connection = getConnection(url, user, password)) {
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, passWord);
            statement.setString(2, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getId(String username) {
        int userId = -1; // Default value if user is not found
        try (Connection connection = getConnection(url, user, password)) {
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    userId = resultSet.getInt("id");
                }
                System.out.println(userId);
                setUserId(userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addBudgetDB() {

        try (Connection connection = getConnection(url, user, password)) {
            String query = "UPDATE users SET total_budget = ? ,rem_budget = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, budget.getTotalBudget());
                statement.setLong(2, budget.getCurrentAmount());
                statement.setInt(3, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateBudgetDB() {

        try (Connection connection = getConnection(url, user, password)) {
            String query = "UPDATE users SET rem_budget = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, budget.getCurrentAmount());
                statement.setInt(2, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getBudgetDB(String username) {
        try (Connection connection = getConnection(url, user, password)) {
            String query = "SELECT rem_budget,total_budget FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    budget = new Budget();
                    budget.setTotalBudgetDB(resultSet.getLong("total_budget"));
                    budget.setCurrentAmount(resultSet.getLong("rem_budget"));


                    System.out.println("Total Budget : " + budget.getTotalBudget());
                    System.out.println("Current Budget :" + budget.getCurrentAmount());

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

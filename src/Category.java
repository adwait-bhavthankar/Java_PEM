import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class Category {
    private Integer categoryId;
   private String name;

    String url = "jdbc:mysql://localhost:3306/expense";  // Root connection, no database specified
    String user = "root";
    String password = "Virat@18";
    String databaseName = "expense";

    public Category() {
    }

    public Category(Integer categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Category(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void saveCategory() {
        try (Connection connection = getConnection(url, user, password)) {
            String query = "INSERT INTO Category (categoryName) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        try ( Connection connection = getConnection(url, user, password)) {
            String query = "SELECT * FROM category";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    Category category=new Category(resultSet.getInt("categoryId"),resultSet.getString("categoryName"));

                    // Set other user details as needed
                    categoryList.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      return categoryList;
    }
}

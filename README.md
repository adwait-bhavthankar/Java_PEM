### Expense Manager Project README

This is a console-based Java application for managing personal expenses. It uses JDBC (Java Database Connectivity) to interact with a MySQL database, allowing users to track their budgets and expenses.

-----

### Key Features

  * **User Management:**
      * **Sign Up:** Create a new user account.
      * **Log In:** Log in to an existing account.
  * **Budget Management:**
      * **Initialize Budget:** Set an initial total budget.
      * **Add Budget:** Increase the total budget.
      * **Update Budget:** The current budget is automatically updated when expenses are added.
  * **Expense and Category Management:**
      * **Add Category:** Create and save new expense categories to the database.
      * **Add Expense:** Record a new expense by specifying the category, amount, and a description.
  * **Expense Reporting:**
      * **Category-wise Report:** View expenses filtered by category.
      * **Monthly Report:** View all expenses for a specific month.
      * **Yearly Report:** View all expenses for a specific year.
      * **File Output:** All reports can be saved to a text file on your local machine.

-----

### Project Structure

  * `Main.java`: The main entry point of the application. It establishes the initial database connection and starts the `Controller` to display the main menu.
  * `Controller.java`: Manages the application's user interface. It displays the main menu, handles user input, and calls the appropriate methods from other classes based on user choices.
  * `User.java`: Represents a user of the application. It handles user-related actions such as signing up, logging in, and interacting with the `users` table in the database.
  * `Budget.java`: Represents the user's budget. It contains methods for setting, adding, and subtracting amounts from the budget.
  * `Expense.java`: Represents a single expense. It contains methods for saving an expense to the `expenses` table and retrieving expenses based on various criteria (month, year, category). It also includes the SQL schema for the `expenses` table as a comment.
  * `Category.java`: Represents an expense category. It contains methods for saving new categories and retrieving all existing categories from the `category` table.
  * `Repository.java`: A singleton class that acts as a central repository to hold a user's expenses and categories in memory after they are retrieved from the database.
  * `DateUtil.java`: A utility class for handling date formatting and parsing.

-----

### Database Setup

This project uses a MySQL database. Before running the application, you need to create the necessary tables. The SQL schema for the `expenses` table is provided in the `Expense.java` file. You will also need to set up tables for `users` and `category`.

Based on the provided code, the schemas for the tables should be:

**`users` table:**

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    total_budget DECIMAL(10, 2),
    rem_budget DECIMAL(10, 2)
);
```

**`category` table:**

```sql
CREATE TABLE category (
    categoryId INT AUTO_INCREMENT PRIMARY KEY,
    categoryName VARCHAR(255) NOT NULL UNIQUE
);
```

**`expenses` table:**

```sql
CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT,
    description VARCHAR(255),
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(categoryId)
);
```

**Note:** Remember to update the `url`, `user`, and `password` variables in `Main.java`, `Expense.java`, `Category.java`, and `User.java` to match your MySQL server configuration.

-----

### How to Run

1.  **Set up the database:**
      * Ensure MySQL is running on `localhost:3306`.
      * Create a database named `expense`.
      * Run the SQL commands above to create the `users`, `category`, and `expenses` tables.
2.  **Compile the Java files:**
      * Make sure you have the MySQL JDBC driver in your project's classpath.
3.  **Run the `Main` class:**
      * The application will start, and you will be presented with a menu of options in the console. Follow the on-screen instructions to interact with the application.

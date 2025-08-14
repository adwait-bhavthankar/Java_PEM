Java Personal Expense Manager (PEM)
A simple console-based Java application for tracking and managing your personal expenses. The Personal Expense Manager (PEM) demonstrates foundational OOP, file handling, and CRUD operations in Java, ideal for small projects, interview demos, or expanding your programming skills.

Features
Add, view, update, and delete expense entries

Categorize and filter expenses (optional, depending on your implementation)

Persistent storage using local files

Simple reports or summaries of spending

Getting Started
Prerequisites
Java Development Kit (JDK) version 8 or above
Check with:

bash
java -version
Installation & Running
Clone the repository:

bash
git clone https://github.com/yourusername/java-pem.git
cd java-pem
Compile the application:

bash
javac Main.java
Run the application:

bash
java Main
Follow the console prompts to manage expenses.

Project Structure
text
java-pem/
│
├── Main.java
├── Expense.java
├── ExpenseManager.java
├── expenses.txt
└── README.md
Main.java — Application entry point.

Expense.java — Represents an expense record.

ExpenseManager.java — Handles CRUD logic and file operations.

expenses.txt — Data file for expense records.

Customization
Add more fields (e.g., payment type, tags)

Implement category-based filtering

Generate more detailed reports

Demo
Record your expenses directly from the console.
Example commands:

Add Expense

View All Expenses

Update an Entry

Delete an Entry

Contributing
Contributions and suggestions are welcome! Fork the repository and submit a pull request.

License
This project is licensed under the MIT License.

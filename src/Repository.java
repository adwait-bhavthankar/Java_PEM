import java.util.*;

public class Repository {

    private  User user;


    public List<Expense> expenseList=new ArrayList<>();
    public List<Category>categoryList=new ArrayList<>();
    private static Repository repository;

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    Repository()
    {

    }
    public Repository(User user) {
        if (user != null) {
            this.user = user;

        } else {
            throw new IllegalArgumentException("User must be initialized to create a Repository");
        }
    }

    public static Repository getRepository()
    {
        if(repository==null)
        {
            repository=new Repository();
        }
        return repository;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }





}

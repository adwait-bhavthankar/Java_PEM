import java.io.*;
import java.sql.Connection;
import java.util.*;

public class Controller {
    private final Scanner sc=new Scanner(System.in);
    private  int choice;
    User user=new User();
    private boolean newUser=true;

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }


    public void displayMenu(Connection connection) throws FileNotFoundException {
        while(true)
        {
            createMenu();
            switch (choice) {
                case 1 :
                    //TODO:Add Budget
                    System.out.println("******* Sign Up *******");
                    createUser();
                    pressKey();
                    break;


                case 2 :
                    //TODO:Add Budget
                    System.out.println("******* LogIn *******");
                    logIn();
                    pressKey();
                    break;

                case 3 :
                    //TODO:Add Budget
                    System.out.println("******* Create Repository *******");
                    createRepo();
                    pressKey();
                    break;

                case 4:

                    System.out.println("******* Initialize Budget*******");
                    createBudget();
                    pressKey();
                    break;



                case 5 :
                    //TODO:Add Expense
                    System.out.println("******* Add Category *******");
                    onAddCategory();
                    pressKey();
                    break;


                case 6 :
                    //TODO:Add Budget
                    System.out.println("******* Add Expense *******");
                    onAddBudget();
                    pressKey();
                    break;
                case 7 :
                    //TODO:Add Budget
                    System.out.println("******* Display Budget *******");
                    onAddExpense();
                    pressKey();
                    break;

                case 8 :
                    //TODO:Add Budget
                    System.out.println("******* Display Categorise Expense *******");
                    displayExpenseListCategorywise(connection);
                    pressKey();
                    break;

                case 9 :
                    //TODO:Add Budget
                    System.out.println("******* Display Monthly Expense *******");
                    displayExpenseListMonthwise();
                    pressKey();
                    break;

                case 10 :
                    //TODO:Add Budget
                    System.out.println("******* Display Yearly Expense *******");
                    displayExpenseListYearwise();
                    pressKey();
                    break;





                case 0 : System.exit(0);
            }

        }
    }

    public void createMenu()
    {
        System.out.println("*******Expense Manager*******");
        System.out.println("1.SignUp");
        System.out.println("2.LogIn");
        System.out.println("3.Initialize Repository");
        System.out.println("4.Initialize Budget");
        System.out.println("5.Add Category");
        System.out.println("6.Add Budget");
        System.out.println("7.Add Expense");
        System.out.println("8.Display  Categories Wise Expense Report");
        System.out.println("9.Display Monthly Expense Report");
        System.out.println("10.Display Yearly Expense Report");
        System.out.println("0.Exit");
        System.out.print("Enter Your Choice : ");
        choice=sc.nextInt();

    }


    public void createUser()
    { String userName;
        String passWord;
        sc.nextLine();
        System.out.println("Enter User Name :  ");
        userName=sc.nextLine();
        System.out.println("Enter Password :  ");
        passWord=sc.nextLine();
        user.signUp(userName,passWord);
        user.saveToDatabase();
        createRepo();
    }

    public void logIn()
    {
        String userName;
        String passWord;
        sc.nextLine();
        if(user.loggedIn){
            System.out.println("User Already LoggedIn !!!");
        }else {
        System.out.println("Enter User Name :  ");
        userName=sc.nextLine();
        System.out.println("Enter Password :  ");
        passWord=sc.nextLine();
        user.loginUser(userName,passWord);
        createRepo();
        setNewUser(false);


        }
    }

    public void createRepo()
    {
        sc.nextLine();
        if(user.loggedIn)
        {
            user.createRepository();
            System.out.println("Repository Is Created");
        }
        else{
            System.out.println("User is Not Logged In");
        }
    }
    public void pressKey()  {
        try{
            System.out.println("Enter Any Key to Continue....");
            System.in.read();
        }catch (IOException ex)
        {
          ex.printStackTrace();
        }
    }

  public void createBudget()
    {
        if(!isNewUser()){
            System.out.println("Budget is Already Initialized !!!");
        }else {
            sc.nextLine();
            System.out.println("******* Adding Budget *******");
            System.out.print("Enter the Amount : ");
            long bud = sc.nextLong();
            user.createBudget(bud);
            user.addBudgetDB();
        }

    }

    public void onAddBudget(){
        sc.nextLine();
        if(user.budget==null && isNewUser())
        {
            System.out.println("Initialize Budget !");
        }
        else {
            System.out.println("Enter Amount : ");
            long bud=sc.nextLong();
            user.createBudget(bud);
            user.budget.addAmount(bud);
            user.addBudgetDB();
            System.out.println("Current Budget : "+user.budget.getCurrentAmount());

        }
    }

    public void onAddCategory()
    {
        sc.nextLine();
        if(user.getRepository()==null)
        {
            System.out.println("Repository is Not Created ! ");
        }
        else {
            System.out.print("Enter the Category : ");
            String cat = sc.nextLine();
            Category category = new Category();
            category.setName(cat);
            category.saveCategory();

        }
    }



    public void onAddExpense()
    {

        System.out.println("******* Adding Expense *******");
        displayCategoryList();

        System.out.print("Enter the Amount : ");
        long amount=sc.nextLong();
        user.budget.subAmount(amount);
        System.out.print("Enter Category Id : ");
        Long id= sc.nextLong();


        Date date=new Date();
        sc.nextLine();
        System.out.print("Enter Description : ");
        String dis=sc.nextLine();
        Expense exp=new Expense(id,amount,date,dis);
    exp.addExpenseToDb(user);
        user.getRepository().expenseList.add(exp);
        user.updateBudgetDB();


    }

    public void displayCategoryList()
    {   sc.nextLine();
        System.out.println("******* Displaying Categorises *******");
        if(user.getRepository()==null)
        {
            System.out.println("Repository is Not Created ! ");
        }
        else {
            Category category=new Category();
            List<Category> catList = category.getAllCategories();
            System.out.println("Id" + "Category Name");
            for (int i = 0; i < catList.size(); i++) {
                Category c = catList.get(i);
                System.out.println((i + 1) + " " + c.getName());
            }
        }
            System.out.println();
    }


    public void displayExpenseListCategorywise(Connection connection) throws FileNotFoundException {   sc.nextLine();
        String strPath=" ",strName=" ";
        int id=0;
        try{
            PrintStream originalOut=System.out;
            System.out.println("******* Displaying Expenses *******");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Enter File Name : ");
            strName=br.readLine();
            File file=new File("D:\\"+strName+".txt");
            PrintStream stream=new PrintStream(file);
            displayCategoryList();
            System.out.println("Enter Category Id: ");
            id=sc.nextInt();
            Expense ep=new Expense();
            ep.getExpensesCategorywise(user,id);
            List<Expense>expList= user.getRepository().expenseList;
            System.setOut(stream);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }
            System.setOut(originalOut);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }

            System.out.println();
        }catch (FileNotFoundException ex)
        {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void displayExpenseListMonthwise() throws FileNotFoundException {   sc.nextLine();
        String strPath=" ",strName=" ";
        int month=0;
        try{
             PrintStream originalOut=System.out;
            System.out.println("******* Displaying Expenses *******");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Enter File Name : ");
            strName=br.readLine();
            File file=new File("D:\\"+strName+".txt");
            PrintStream stream=new PrintStream(file);
            System.out.println("Enter Month Number: ");
            month=sc.nextInt();
            Expense ep=new Expense();
            ep.getExpensesMonthwise(user,month);
            List<Expense>expList= user.getRepository().expenseList;
            System.setOut(stream);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }
            System.setOut(originalOut);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }

            System.out.println();
        }catch (FileNotFoundException ex)
        {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void displayExpenseListYearwise() throws FileNotFoundException {   sc.nextLine();
        String strPath=" ",strName=" ";
        int Year=0;
        try{
            PrintStream originalOut=System.out;
            System.out.println("******* Displaying Expenses *******");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Enter File Name : ");
            strName=br.readLine();
            File file=new File("D:\\"+strName+".txt");
            PrintStream stream=new PrintStream(file);
            System.out.println("Enter Year Number: ");
            Year=sc.nextInt();
            Expense ep=new Expense();
            ep.getExpensesYearwise(user,Year);
            List<Expense>expList= user.getRepository().expenseList;
            System.setOut(stream);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }
            System.setOut(originalOut);
            System.out.println(" "+"CategoryId"+" "+"Date"+" "+"Description"+" "+"Amount");
            for (int i=0;i<expList.size();i++)
            {
                Expense e =expList.get(i);
                System.out.println((i+1)+" "+e.categoryId+" "+DateUtil.displayString(e.date)+" "+" "+e.getDescription()+" "+e.getAmount());
            }

            System.out.println();
        }catch (FileNotFoundException ex)
        {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }









}

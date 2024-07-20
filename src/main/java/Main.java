import DAO.ExpenseDAO;
import DAO.GroupDAO;
import DAO.UserDAO;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import controllers.BalanceSheetController;
import controllers.ExpenseController;
import controllers.GroupController;
import controllers.UserController;
import models.Group;
import models.Split;
import models.SplitType;
import models.User;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        GroupDAO groupDAO = new GroupDAO();
        ExpenseDAO expenseDAO = new ExpenseDAO();

        UserController userController = new UserController(userDAO);
        ExpenseController expenseController = new ExpenseController(expenseDAO);
        GroupController groupController = new GroupController(groupDAO, expenseDAO);
        BalanceSheetController balanceSheetController = new BalanceSheetController();

        List<User> listUser = new ArrayList<>();


        setupDB(listUser);
        while(true){
            displayMenu();
            int input = sc.nextInt();
            switch (input){
                case 1 -> addUser(userController, listUser);
                case 2 -> createGroup(sc, groupController);
                case 3 -> addUsersToGroup(groupController, sc, userController);
                case 4 -> displayUsersGroup(userController, sc, groupController);
                case 5 -> displayGroupInfo(groupController);
                case 6 -> displayUsers(userController);
                case 7 -> createExpense(groupController, sc, userController);
                case 8 -> displayUserBalance(userController, sc, groupController, balanceSheetController);
                case 9 -> displayGroupBalanceSheet(sc, groupController, balanceSheetController);
                case 10 -> {
                    return;
                }
                default -> System.out.println("Please Enter a valid Input");
            }
        }
    }

    private static void displayGroupBalanceSheet(Scanner sc, GroupController groupController, BalanceSheetController balanceSheetController) {
        Group group = getGroupInputDetails(groupController, sc);
        balanceSheetController.displayGroupBalanceSheet(group);
    }

    private static void displayUsersGroup(UserController userController, Scanner sc, GroupController groupController) {
        Group group = getGroupInputDetails(groupController, sc);
        System.out.println("Users List for the Group "+group.getGroupName());
        System.out.println(group.getListUsers());
    }

    private static void displayUserBalance(UserController userController, Scanner sc, GroupController groupController, BalanceSheetController balanceSheetController) {
        Group group = getGroupInputDetails(groupController, sc);
        System.out.println(group.getListUsers());
        System.out.println("Select a User to get his Balance Sheet");
        String userName = sc.nextLine().trim();
        List<User> listUserBalanceSheet = userController.getUsersFromUserNames(Collections.singleton(userName));
        if(!listUserBalanceSheet.isEmpty()){
            System.out.println("Balance Sheet for the User " +listUserBalanceSheet.get(0).getUserName() + " part of Group " + group.getGroupName());
            balanceSheetController.displayUserBalanceSheet(listUserBalanceSheet.get(0));
        }else{
            System.out.println("Please Enter a valid Username from the list");
        }
    }

    private static void createExpense(GroupController groupController, Scanner sc, UserController userController){
        Group groupDetails = getGroupInputDetails(groupController, sc);
        if (groupDetails == null) return;
        System.out.println("Please provide an Expense Name");
        String expenseName = sc.nextLine().trim();
        System.out.println("Please provide the Total Amount For this Expense");
        Double amount = sc.nextDouble();
        sc.nextLine();
        SplitType splitType = SplitType.EQUAL;
        System.out.println(groupDetails.getListUsers());
        System.out.println("Please Select Users in the above who paid this Expense");
        String userName = sc.nextLine().trim();
        List<User> listExpensePaidUsers = userController.getUsersFromUserNames(Collections.singleton(userName));
        if(listExpensePaidUsers.isEmpty()){
            System.out.println("Please Select a valid User");
            return;
        }
        List<Split> listSplit = new ArrayList<>();
        List<User> listGroupUser = groupDetails.getListUsers();
        for(User user : listGroupUser){
            listSplit.add(new Split(user, amount/ listGroupUser.size()));
        }
        groupController.addGroupExpense(groupDetails, expenseName, amount, splitType, listExpensePaidUsers.get(0), listSplit);
    }

    private static void displayGroupInfo(GroupController groupController) {
        groupController.displayGroupDetails();
    }

    private static void displayUsers(UserController userController){
        userController.getListUsers();
    }
    private static void addUsersToGroup(GroupController groupController, Scanner sc, UserController userController) {
        Group groupDetails = getGroupInputDetails(groupController, sc);
        userController.getListUsers();
        System.out.println("Select the number of users to add to the group");
        int n = sc.nextInt();
        sc.nextLine();
        System.out.println("Select the Username of Users who need to be added to the group");
        Set<String> setUserName = new HashSet<>();
        for(int i=0;i<n;i++){
            String userName = sc.nextLine().trim();
            setUserName.add(userName);
        }
        List<User> listGroupUser = userController.getUsersFromUserNames(setUserName);
        if(!listGroupUser.isEmpty()){
            System.out.println(groupDetails);
            System.out.println(listGroupUser);
            groupController.addUsersToGroup(groupDetails, listGroupUser);
        }else{
            System.out.println("Please Enter a valid Group Name from the List");
        }
    }

    private static void createGroup(Scanner sc, GroupController groupController) {
        System.out.println("Please Enter Group Name and Description");
        sc.nextLine();
        String groupName = sc.nextLine().trim();
        String groupDesc = sc.nextLine().trim();
        groupController.addGroup(new Group(groupName, groupDesc));
    }

    private static void addUser(UserController userController, List<User> listUser) {
        userController.addUser(listUser);
    }

    private static void setupDB(List<User> listUser) {
        for(int i=0;i<10;i++){
            Faker faker = new Faker();
            User user = new User(faker.name().fullName());
            listUser.add(user);
        }

    }

    private static Group getGroupInputDetails(GroupController groupController, Scanner sc) {
        groupController.displayGroupDetails();
        System.out.println("Specify the Group Name to add the Users from the above list");
        sc.nextLine();
        String groupName = sc.nextLine().trim();
        Group groupDetails;
        Optional<Group> groupInDB = groupController.getGroupNameFromGroup(groupName);
        if(groupInDB.isEmpty()){
            System.out.println("Please Enter a valid Group Name from the List");
            return null;
        }else{
            groupDetails = groupInDB.get();
        }
        return groupDetails;
    }

    private static void displayMenu() {
        System.out.println("1️⃣ - Add Users");
        System.out.println("2️⃣ - Create a Group");
        System.out.println("3️⃣ - Add Users to the Group");
        System.out.println("4️⃣ - Display Group User Details");
        System.out.println("5️⃣ - Display Group Details");
        System.out.println("6️⃣ - View All Users");
        System.out.println("7️⃣-  Create an Expense");
        System.out.println("8️⃣ - View User Balance sheet");
        System.out.println("9️⃣ - View Group Balance sheet");
        System.out.println("🔟 - Exit");
    }
}
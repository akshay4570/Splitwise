package controllers;

import DAO.ExpenseDAO;
import DAO.GroupDAO;
import models.*;

import java.util.*;

public class GroupController {

    private final GroupDAO groupDAO;
    private DBResponse dbResponse;
    private ExpenseController expenseController;
    private final ExpenseDAO expenseDAO;

    public GroupController(GroupDAO groupDAO, ExpenseDAO expenseDAO){
        this.groupDAO = groupDAO;
        this.expenseDAO = expenseDAO;
        expenseController = new ExpenseController(this.expenseDAO);
    }

    public void addGroup(Group group){
        dbResponse = groupDAO.addGroupToDB(group);
        displayMessageGroup(group, "Insertion");
    }

    public void deleteGroup(Set<Group> setGroup){
        if(!setGroup.isEmpty()){
            Set<UUID> setUUId = new HashSet<>();
            dbResponse = groupDAO.deleteGroupFromDB(setUUId);
        }
        displayMessageGroup(null, "Deletion");
    }

    public void updateGroup(Group group){
        dbResponse = groupDAO.updateGroupInDB(group);
        displayMessageGroup(group, "Updation");
    }

    public void addUsersToGroup(Group group, List<User> listUsers){
        if(group != null && !listUsers.isEmpty()){
            dbResponse = groupDAO.insertUsersToGroup(group.getGroupId(), listUsers);
        }
        displayMessageUsers(group, "Insertion");
    }

    public void deleteUsersFromGroup(Group group, List<User> listUsers){
        if(group != null && !listUsers.isEmpty()){
            Set<UUID> setUUId = new HashSet<>();
            for(User objUser : listUsers){
                setUUId.add(objUser.getUserId());
            }
            dbResponse = groupDAO.deleteUsersFromGroup(group.getGroupId(), setUUId);
        }
        displayMessageUsers(group, "Deletion");
    }

    private void displayMessageGroup(Group group, String dmlOperation){
        if(dmlOperation.equals("Deletion")){
            if(dbResponse == DBResponse.SUCCESS){
                System.out.println(dmlOperation + " of Group was successful");
            }else{
                System.out.println(dmlOperation + " of Group failed");
            }
        }
        else{
            if(dbResponse == DBResponse.SUCCESS){
                System.out.println(dmlOperation + " of Group with " + group.getGroupName() + " was successful");
            }else{
                System.out.println(dmlOperation + " of Group with " + group.getGroupName() + " failed");
            }
        }
    }

    private void displayMessageUsers(Group group, String dmlOperation){
        if(dmlOperation.equals("Deletion")){
            if(dbResponse == DBResponse.SUCCESS){
                System.out.println(dmlOperation + " of Users from the group " + group.getGroupName() + " was successful");
            }else{
                System.out.println(dmlOperation + " of Group " + group.getGroupName() + " failed");
            }
        }
        else{
            if(dbResponse == DBResponse.SUCCESS){
                System.out.println(dmlOperation + " of Users to the Group with " + group.getGroupName() + " was successful");
            }else{
                System.out.println(dmlOperation + " of Users to the Group with " + group.getGroupName() + " failed");
            }
        }
    }

    public void addGroupExpense(Group group, String expenseName, Double amount, SplitType splitType, User expensePaidByUser, List<Split> listSplit){
        Expense expense = expenseController.createExpense(group, expenseName, amount, splitType,  expensePaidByUser, listSplit);
        group.setListExpense(Collections.singletonList(expense));
    }

}

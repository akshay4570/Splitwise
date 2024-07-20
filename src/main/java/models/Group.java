package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group {
    private UUID groupId;
    private String groupName;
    private String groupDescription;
    private List<User> listUsers;
    private List<Expense> listExpense;

    public Group(String groupName, String groupDescription) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.listUsers = new ArrayList<>();
        this.listExpense = new ArrayList<>();
    }

    public UUID getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public List<Expense> getListExpense() {
        return listExpense;
    }


    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public void setListExpense(List<Expense> listExpense) {
        this.listExpense = listExpense;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupDescription='" + groupDescription + '\'' +
                ", listUsers=" + listUsers +
                ", listExpense=" + listExpense +
                '}';
    }
}

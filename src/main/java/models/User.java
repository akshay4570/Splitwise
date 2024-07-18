package models;

import java.util.UUID;

public class User {

    private UUID userId;
    private String userName;
    private BalanceSheet balanceSheet;
    public User(String userName) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        this.balanceSheet = new BalanceSheet();
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}

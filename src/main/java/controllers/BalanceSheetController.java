package controllers;

import models.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BalanceSheetController {

    public void recalculateBalance(Group group){
        List<Expense> listExpense = group.getListExpense();
        List<User> listUsers= group.getListUsers();
        for(Expense objExpense : listExpense){

        }
    }
    public void displayUserBalanceSheet(User user){
        System.out.println("Details about the Balance Sheet of the User "+user.getUserName());
        BalanceSheet balanceSheet = user.getBalanceSheet();

        System.out.println("==============================================================================================================================================================");
        System.out.println("Total Payment Done by the User " + user.getUserName() + " Rs" + balanceSheet.getTotalPayment());
        System.out.println("Total Personal Expenditure by the User " + user.getUserName() + " Rs" + balanceSheet.getTotalPersonalExpense());
        System.out.println("Total Amount Owed by the User " + user.getUserName() + " Rs" + balanceSheet.getTotalOwed());
        System.out.println("Total Amount Yet to be Received by the User " + user.getUserName() + " Rs" + balanceSheet.getTotalToReceive());
        for(String userName : balanceSheet.getMapUserBalance().keySet()){
            Balance balance = balanceSheet.getMapUserBalance().get(userName);
            System.out.println("User with Username "+userName);
            System.out.println("You get back : Rs "+balance.getAmountToReceive());
            System.out.println("You Owe : Rs "+balance.getAmountToReceive());
        }
        System.out.println("==============================================================================================================================================================");
    }
}

package controllers;

import models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceSheetController {
    private Map<User, Balance> mapUserBalance;
    private Map<User, Double> mapTotalPersonalExpense;
    private Map<User, Double> mapTotalPaymentDoneByUser;

    public BalanceSheetController(){
        mapUserBalance = new HashMap<>();
        mapTotalPersonalExpense = new HashMap<>();
        mapTotalPaymentDoneByUser = new HashMap<>();
    }
    public void recalculateBalance(Group group){
        List<Expense> listExpense = group.getListExpense();
        List<User> listUsers = group.getListUsers();
        for(Expense objExpense : listExpense){
            User expensePaidUser = objExpense.getExpensePaidByUser();
            Double amount = objExpense.getAmount();
            for(Split split : objExpense.getListSplit()){
                User currentUser = split.getUser();
                Balance balance;
                if(mapTotalPersonalExpense.containsKey(currentUser)){
                    mapTotalPersonalExpense.put(currentUser, mapTotalPersonalExpense.get(currentUser) + split.getAmountOwed());
                }else{
                    mapTotalPersonalExpense.put(currentUser, split.getAmountOwed());
                }
                if(currentUser == expensePaidUser){
                    if(mapTotalPaymentDoneByUser.containsKey((currentUser))){
                        mapTotalPaymentDoneByUser.put(currentUser, mapTotalPaymentDoneByUser.get(currentUser) + split.getAmountOwed());
                    }else{
                        mapTotalPaymentDoneByUser.put(currentUser, split.getAmountOwed());
                    }
                    if(mapUserBalance.containsKey(currentUser)){
                        balance = mapUserBalance.get(currentUser);
                        balance.setAmountToReceive(amount - split.getAmountOwed());
                    }else{
                        balance = new Balance(0.0, amount - split.getAmountOwed());
                    }
                    mapUserBalance.put(currentUser, balance);
                }else{
                    if(mapUserBalance.containsKey((currentUser))){
                        balance = mapUserBalance.get(currentUser);
                        balance.setAmountOwed(balance.getAmountOwed() + split.getAmountOwed());
                    }else{
                        balance = new Balance(split.getAmountOwed(), 0.0);
                    }
                    mapUserBalance.put(currentUser, balance);
                }
            }
        }
        for(User user : listUsers){
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setMapUserBalance(mapUserBalance);
            balanceSheet.setTotalPayment(mapTotalPaymentDoneByUser.get(user));
            balanceSheet.setTotalPersonalExpense(mapTotalPersonalExpense.get(user));
            balanceSheet.setTotalOwed(mapUserBalance.get(user).getAmountOwed());
            balanceSheet.setTotalToReceive(mapUserBalance.get(user).getAmountToReceive());
            user.setBalanceSheet(balanceSheet);
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
        for(User objUser: balanceSheet.getMapUserBalance().keySet()){
            Balance balance = balanceSheet.getMapUserBalance().get(objUser);
            System.out.println("User with Username "+objUser.getUserName());
            System.out.println("You get back : Rs "+balance.getAmountToReceive());
            System.out.println("You Owe : Rs "+balance.getAmountToReceive());
        }
        System.out.println("==============================================================================================================================================================");
    }
}

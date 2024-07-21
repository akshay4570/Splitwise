package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BalanceSheetController {
    private Map<User, Balance> mapUserBalance;
    private Map<User, Double> mapTotalPersonalExpense;
    private Map<User, Double> mapTotalPaymentDoneByUser;
    private Map<User, List<PaymentGraph> >mapUserAmountOwed;

    public BalanceSheetController(){
        mapUserBalance = new HashMap<>();
        mapTotalPersonalExpense = new HashMap<>();
        mapTotalPaymentDoneByUser = new HashMap<>();
        mapUserAmountOwed = new HashMap<>();
    }
    public void recalculateBalance(Group group){
        List<Expense> listExpense = group.getListExpense();
        System.out.println("List Expense " +listExpense);
        List<User> listUsers = group.getListUsers();
        for(Expense objExpense : listExpense){
            User expensePaidUser = objExpense.getExpensePaidByUser();
            Double amount = objExpense.getAmount();
            List<PaymentGraph> listUserAmount = new ArrayList<>();
            for(Split split : objExpense.getListSplit()){
                User currentUser = split.getUser();
                Balance balance;
                if(mapTotalPersonalExpense.containsKey(currentUser)){
                    mapTotalPersonalExpense.put(currentUser, mapTotalPersonalExpense.get(currentUser) + split.getAmountOwed());
                }else{
                    mapTotalPersonalExpense.put(currentUser, split.getAmountOwed());
                }
                if(currentUser.getUserId().equals(expensePaidUser.getUserId())){
                    if(mapTotalPaymentDoneByUser.containsKey((currentUser))){
                        mapTotalPaymentDoneByUser.put(currentUser, mapTotalPaymentDoneByUser.get(currentUser) + amount);
                    }else{
                        mapTotalPaymentDoneByUser.put(currentUser, amount);
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
                    PaymentGraph paymentGraph = new PaymentGraph();
                    paymentGraph.setMapUserAmount(currentUser, split.getAmountOwed());
                    listUserAmount.add(paymentGraph);
                }
            }
            mapUserAmountOwed.put(expensePaidUser, listUserAmount);
        }
        System.out.println("Map Balance "+mapUserBalance);
        for(User user : listUsers){
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setMapUserBalance(mapUserBalance);
            if(mapTotalPaymentDoneByUser.containsKey(user)) {
                balanceSheet.setTotalPayment(mapTotalPaymentDoneByUser.get(user));
            }
            if(mapTotalPersonalExpense.containsKey(user)){
                balanceSheet.setTotalPersonalExpense(mapTotalPersonalExpense.get(user));
            }
            if(mapUserBalance.containsKey(user)) {
                balanceSheet.setTotalOwed(mapUserBalance.get(user).getAmountOwed());
            }
            if(mapUserBalance.containsKey((user))) {
                balanceSheet.setTotalToReceive(mapUserBalance.get(user).getAmountToReceive());
            }
            if(mapUserAmountOwed.containsKey(user)){
                balanceSheet.setListUserOwed(mapUserAmountOwed.get(user));
            }
            user.setBalanceSheet(balanceSheet);
            System.out.println(user.getBalanceSheet());
        }
    }
    public void displayUserBalanceSheet(User user){
        System.out.println("Details about the Balance Sheet of the User "+user.getUserName());
        BalanceSheet balanceSheet = user.getBalanceSheet();

        System.out.println("==============================================================================================================================================================");
        System.out.println("Total Payment Done by the User " + user.getUserName() + " Rs " + balanceSheet.getTotalPayment());
        System.out.println("Total Personal Expenditure by the User " + user.getUserName() + " Rs " + balanceSheet.getTotalPersonalExpense());
        System.out.println("Total Amount Owed by the User " + user.getUserName() + " Rs " + balanceSheet.getTotalOwed());
        System.out.println("Total Amount Yet to be Received by the User " + user.getUserName() + " Rs " + balanceSheet.getTotalToReceive());

        Balance balance = balanceSheet.getMapUserBalance().get(user);
        System.out.println("User with Username "+user.getUserName());
        System.out.println("You get back : Rs "+balance.getAmountToReceive());
        System.out.println("You Owe : Rs "+balance.getAmountOwed());

        System.out.println("==============================================================================================================================================================");
    }

    public void displayGroupBalanceSheet(Group group){
        System.out.println("Details of Balance of all Users of the Group "+group.getGroupName());;
        List<User> listUser = group.getListUsers();
        System.out.println("==============================================================================================================================================================");
        System.out.println("Before Simplifying Transactions");
        for(User user : listUser){
            Balance balance = user.getBalanceSheet().getMapUserBalance().get(user);
            System.out.println("User with Username "+user.getUserName());
            System.out.println("You get back : Rs "+balance.getAmountToReceive());
            System.out.println("You Owe : Rs "+balance.getAmountOwed());
        }
        Map<User, List<PaymentGraph> > mapPayment = new HashMap<>();
        for(User user : listUser){
            mapPayment.put(user, user.getBalanceSheet().getListUserOwed());
        }
        List<List<Object> > listGraph = simplifyTransactions(mapPayment);
        for(List<Object> obj : listGraph){
            System.out.println(obj.get(0) + " -> " + obj.get(1) + " -> Rs " + obj.get(2));
        }
        System.out.println("==============================================================================================================================================================");
    }

    public List<List<Object>> simplifyTransactions(Map<User, List<PaymentGraph>> mapPayment){
        Map<User, Double> mapDegree = new HashMap<>();
        for(User userFrom : mapPayment.keySet()){
            for(PaymentGraph paymentGraph : mapPayment.get(userFrom)){
                for(User userTo : paymentGraph.getMapUserAmount().keySet()){
                    mapDegree.put(userFrom, mapDegree.getOrDefault(userFrom, 0.0) + paymentGraph.getMapUserAmount().get(userTo));
                    mapDegree.put(userTo, mapDegree.getOrDefault(userTo, 0.0) - paymentGraph.getMapUserAmount().get(userTo));
                }
            }
        }
        System.out.println("Map Before " + mapDegree.toString());
        mapDegree = mapDegree.entrySet().stream()
                .filter(obj -> obj.getValue() != 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        System.out.println("Map After " + mapDegree.toString());
        List<List<Object>> list2D = new ArrayList<>();
        List<List<Object>> listGraph = new ArrayList<>();
        for(User user : mapDegree.keySet()){
            List<Object> listInner = new ArrayList<>();
            listInner.add(user.getUserName());
            listInner.add(mapDegree.get(user));
            list2D.add(listInner);
        }
        System.out.println("List 2D " + list2D.toString());
        Integer minTransactions = dfs(list2D, 0, listGraph);
        System.out.println("Total Transactions are reduced to "+minTransactions);
        return listGraph;
    }

    private Integer dfs(List<List<Object>> list2D, int i, List<List<Object>> listGraph) {
        if(list2D.isEmpty() || i >= list2D.size()) return  0;

        if(Double.compare(Double.valueOf(list2D.get(i).get(1).toString()),0.0) == 0 ){
            return dfs(list2D, i+1, listGraph);
        }

        Double currentVal = Double.valueOf(list2D.get(i).get(1).toString());
        int minTransaction = Integer.MAX_VALUE;

        for(int j=i+1; j<list2D.size();j++){
            Double nextVal = Double.valueOf(list2D.get(j).get(1).toString());
            if(Double.compare(currentVal * nextVal, 0.0) == -1){
                list2D.get(j).set(1, currentVal + nextVal);
                if(Double.compare(nextVal, 0) == -1){
                    listGraph.add(List.of(list2D.get(j).get(0),list2D.get(i).get(0), Math.abs(nextVal)));
                }else{
                    listGraph.add(List.of(list2D.get(i).get(0),list2D.get(j).get(0), Math.abs(currentVal)));
                }
                minTransaction = Math.min(minTransaction, 1 + dfs(list2D, i+1, listGraph));
                list2D.get(j).set(1, nextVal);
                if(Double.compare(currentVal + nextVal, 0.0) == 0){
                    break;
                }
            }
        }
        return minTransaction;
    }
}

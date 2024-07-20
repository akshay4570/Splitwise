package controllers;

import DAO.ExpenseDAO;
import controllers.Split.ExpenseSplit;
import models.*;

import java.util.Collections;
import java.util.List;

public class ExpenseController {

    private final ExpenseDAO expenseDAO;
    private DBResponse dbResponse;
    private BalanceSheetController balanceSheetController;

    public ExpenseController(ExpenseDAO expenseDAO){
        this.expenseDAO = expenseDAO;
        balanceSheetController = new BalanceSheetController();
    }
    public void createExpense(Group group, String expenseName, Double amount, SplitType splitType, User expensePaidByUser, List<Split> listSplit) {
        Expense expense;
        ExpenseSplit expenseSplit = SplitFactory.getSplitType(splitType);

        if(expenseSplit.validateSplit(listSplit, amount)){
            expense = new Expense(expenseName, amount, splitType, expensePaidByUser, listSplit);
            dbResponse = expenseDAO.addNewExpenseToDB(group.getGroupId(), expense);
            printDBResonse(dbResponse, group.getGroupName());
            group.setListExpense(Collections.singletonList(expense));
            balanceSheetController.recalculateBalance(group);
        }else{
            throw new IllegalStateException("Verify the Split Type");
        }
    }

    private void printDBResonse(DBResponse dbResponse, String groupName) {
        System.out.println(dbResponse == DBResponse.SUCCESS ? "Added Expense to Group " + groupName : "Failed to add Expense");
    }
}

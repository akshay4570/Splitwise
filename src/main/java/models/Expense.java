package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Expense {
    private UUID expenseId;
    private String expenseName;
    private Double amount;
    private SplitType splitType;
    private User expensePaidByUser;
    private List<Split> listSplit = new ArrayList<>();

    public Expense(String expenseName, Double amount, SplitType splitType, User expensePaidByUser, List<Split> listSplit) {
        this.expenseId = UUID.randomUUID();
        this.expenseName = expenseName;
        this.amount = amount;
        this.splitType = splitType;
        this.expensePaidByUser = expensePaidByUser;
        this.listSplit.addAll(listSplit);
    }
}

package models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BalanceSheet {
    private UUID balanceSheetId;
    private Map<User, Balance> mapUserBalance;
    private Double totalPersonalExpense;
    private Double totalPayment;
    private Double totalOwed;
    private Double totalToReceive;

    public BalanceSheet() {
        this.balanceSheetId = UUID.randomUUID();
        this.mapUserBalance = new HashMap<>();
        this.totalPersonalExpense = 0.0;
        this.totalPayment = 0.0;
        this.totalOwed = 0.0;
        this.totalToReceive = 0.0;
    }

    public Map<User, Balance> getMapUserBalance() {
        return mapUserBalance;
    }

    public void setMapUserBalance(Map<User, Balance> mapUserBalance) {
        this.mapUserBalance = mapUserBalance;
    }

    public Double getTotalPersonalExpense() {
        return totalPersonalExpense;
    }

    public void setTotalPersonalExpense(Double totalPersonalExpense) {
        this.totalPersonalExpense = totalPersonalExpense;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Double getTotalOwed() {
        return totalOwed;
    }

    public void setTotalOwed(Double totalOwed) {
        this.totalOwed = totalOwed;
    }

    public Double getTotalToReceive() {
        return totalToReceive;
    }

    public void setTotalToReceive(Double totalToReceive) {
        this.totalToReceive = totalToReceive;
    }

    @Override
    public String toString() {
        return "BalanceSheet{" +
                "balanceSheetId=" + balanceSheetId +
                ", mapUserBalance=" + mapUserBalance +
                ", totalPersonalExpense=" + totalPersonalExpense +
                ", totalPayment=" + totalPayment +
                ", totalOwed=" + totalOwed +
                ", totalToReceive=" + totalToReceive +
                '}';
    }
}

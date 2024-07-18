package models;

public class Balance {
    private Double amountOwed;
    private Double amountToReceive;

    public Balance(Double amountOwed, Double amountToReceive) {
        this.amountOwed = amountOwed;
        this.amountToReceive = amountToReceive;
    }

    public Double getAmountOwed() {
        return amountOwed;
    }

    public Double getAmountToReceive() {
        return amountToReceive;
    }
}

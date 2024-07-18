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

    public void setAmountOwed(Double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setAmountToReceive(Double amountToReceive) {
        this.amountToReceive = amountToReceive;
    }
}

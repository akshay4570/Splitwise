package models;

public class Split {
    private User user;
    private Double amountOwed;

    public Split(User user, Double amountOwed) {
        this.user = user;
        this.amountOwed = amountOwed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(Double amountOwed) {
        this.amountOwed = amountOwed;
    }
}

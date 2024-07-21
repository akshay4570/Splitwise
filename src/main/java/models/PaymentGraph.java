package models;

import java.util.HashMap;
import java.util.Map;

public class PaymentGraph {
    private Map<User, Double> mapUserAmount;

    public PaymentGraph() {
        this.mapUserAmount = new HashMap<>();
    }

    public Map<User, Double> getMapUserAmount() {
        return mapUserAmount;
    }

    public void setMapUserAmount(User user, Double amount) {
        mapUserAmount.put(user, amount);
    }
}

package controllers.Split;

import models.Split;

import java.util.List;

public interface ExpenseSplit {
    public boolean validateSplit(List<Split> listSplit, Double totalAmount);
}

package controllers.Split;

import models.Split;

import java.util.List;

public class PercentageSplit implements ExpenseSplit{
    @Override
    public boolean validateSplit(List<Split> listSplit, Double totalAmount) {
        Double totalSum = 0.0;
        for(Split split : listSplit){
            totalSum += split.getAmountOwed();
        }
        return Double.compare(totalAmount, totalSum) == 0;
    }
}

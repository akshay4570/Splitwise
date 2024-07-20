package controllers.Split;

import models.Split;

import java.util.List;

public class EqualSplit implements ExpenseSplit{
    @Override
    public boolean validateSplit(List<Split> listSplit, Double totalAmount) {
        Double amount = totalAmount/listSplit.size();
        for(Split split : listSplit){
            if(Double.compare(split.getAmountOwed(), amount) != 0){
                return false;
            }
        }
        return true;
    }
}

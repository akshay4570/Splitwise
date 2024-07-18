package controllers;

import controllers.Split.EqualSplit;
import controllers.Split.ExpenseSplit;
import controllers.Split.PercentageSplit;
import controllers.Split.UnEqualSplit;
import models.SplitType;

public class SplitFactory {
    public static ExpenseSplit getSplitType(SplitType splitType){
        ExpenseSplit expenseSplit;
        switch (splitType){
            case EQUAL -> expenseSplit = new EqualSplit();
            case UNEQUAL -> expenseSplit = new UnEqualSplit();
            case PERCENTAGE -> expenseSplit = new PercentageSplit();
            default -> expenseSplit = null;
        }
        return expenseSplit;
    }
}

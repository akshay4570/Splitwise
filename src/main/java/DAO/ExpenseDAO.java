package DAO;

import controllers.DBResponse;
import models.Expense;

import java.util.*;

public class ExpenseDAO {
    private Map<UUID, List<Expense> > mapGroupExpense;

    private DBResponse dbResponse;

    public ExpenseDAO(){
        mapGroupExpense = new HashMap<>();
    }

    public Map<UUID, List<Expense>> getMapGroupExpense() {
        return mapGroupExpense;
    }

    public DBResponse addNewExpenseToDB(UUID groupId, Expense expense) {
        try {
            if(mapGroupExpense.containsKey(groupId)){
                mapGroupExpense.get(groupId).add(expense);
            }else{
                mapGroupExpense.put(groupId, Collections.singletonList(expense));
            }
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }
}

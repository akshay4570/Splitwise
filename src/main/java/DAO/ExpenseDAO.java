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
                mapGroupExpense.put(groupId, new ArrayList<>(Arrays.asList(expense)));
            }
            System.out.println("Map "+mapGroupExpense);
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            System.out.println("Exception" +e.getMessage());
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }
}

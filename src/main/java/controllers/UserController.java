package controllers;

import DAO.UserDAO;
import models.BalanceSheet;
import models.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserController {
    private final UserDAO userDAO;
    private DBResponse dbResponse;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(List<User> listUser){
        if(!listUser.isEmpty()){
            dbResponse = userDAO.insertUserToDB(listUser);
        }
        databaseResponseMessage(dbResponse, "Insertion");
    }

    public void deleteUser(List<User> listUser){
        Set<UUID> setUsers = new HashSet<>();
        if(!listUser.isEmpty()){
            for(User objUser : listUser){
                setUsers.add(objUser.getUserId());
            }
            dbResponse = userDAO.deleteUserInDB(setUsers);
        }
        databaseResponseMessage(dbResponse, "Deletion");
    }

    public void updateUser(List<User> listUser){
        Map<UUID, User> mapUser;
        if(!listUser.isEmpty()){
            mapUser = listUser.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
            dbResponse = userDAO.updateUserInDB(mapUser);
        }
        databaseResponseMessage(dbResponse, "Updation");
    }
    private void databaseResponseMessage(DBResponse dbResponse, String dmlOperation) {
        if(dbResponse == DBResponse.SUCCESS){
            System.out.println(dmlOperation + " of Users was Successful");
        }else{
            System.out.println(dmlOperation + " of Users Failed");
        }
    }
}

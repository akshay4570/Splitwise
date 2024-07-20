package DAO;

import controllers.DBResponse;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {
    private List<User> listUsers;
    private DBResponse dbResponse;
    public UserDAO() {
        this.listUsers = new ArrayList<>();
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public DBResponse insertUserToDB(List<User> listUser){
        try {
            listUsers.addAll(listUser);
            //Insert into DB
            dbResponse = DBResponse.SUCCESS;
        }catch(Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse deleteUserInDB(Set<UUID> setUserId){
        try {
            listUsers.removeIf(obj -> setUserId.contains(obj.getUserId()));
            //Update to DB;
            dbResponse = DBResponse.SUCCESS;
        }catch(Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse updateUserInDB(Map<UUID,User> mapUser){
        try {
            listUsers.stream()
                    .filter(obj -> mapUser.containsKey(obj.getUserId()))
                    .map(obj -> mapUser.get(obj.getUserId()))
                    .collect(Collectors.toList());
            //Update to DB;
            dbResponse = DBResponse.SUCCESS;
        }catch(Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "listUsers=" + listUsers +
                '}';
    }
}

package DAO;

import controllers.DBResponse;
import models.Group;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class GroupDAO {
    private List<Group> listGroups;
    private DBResponse dbResponse;
    private Map<UUID, List<User> > mapUserDetails;

    public GroupDAO() {
        this.listGroups = new ArrayList<>();
        this.mapUserDetails = new HashMap<>();
    }

    public List<Group> getListGroups() {
        return listGroups;
    }

    public Map<UUID, List<User>> getMapUserDetails() {
        return mapUserDetails;
    }

    public DBResponse addGroupToDB(Group group){
        try {
            listGroups.add(group);
            //Insert it to DB;
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse deleteGroupFromDB(Set<UUID> setGroups){
        try {
            listGroups.removeIf(obj -> setGroups.contains(obj.getGroupId()));
            //Delete in DB;
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse updateGroupInDB(Group group){
        try {
            listGroups.stream()
                    .filter(obj -> obj.getGroupId() == group.getGroupId())
                    .map(obj -> group)
                    .collect(Collectors.toList());
            //Update in DB
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse insertUsersToGroup(UUID groupId, List<User> listUsers){
        try{
            if(mapUserDetails.containsKey(groupId)){
                mapUserDetails.get(groupId).addAll(listUsers);
            }else{
                mapUserDetails.put(groupId, listUsers);
            }
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    public DBResponse deleteUsersFromGroup(UUID groupId, Set<UUID> setUsers){
        try{
            if(mapUserDetails.containsKey(groupId)){
                mapUserDetails.get(groupId).removeIf(obj -> setUsers.contains(obj.getUserId()));
                if(mapUserDetails.get(groupId).isEmpty()){
                    mapUserDetails.remove(groupId);
                }
            }
            dbResponse = DBResponse.SUCCESS;
        }catch (Exception e){
            dbResponse = DBResponse.FAILURE;
        }
        return dbResponse;
    }

    @Override
    public String toString() {
        return "GroupDAO{" +
                "listGroups=" + listGroups +
                ", mapUserDetails=" + mapUserDetails +
                '}';
    }
}

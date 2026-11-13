package se.chalmers.group21.spa.model.Group;

import se.chalmers.group21.spa.model.User.User;

import java.util.HashMap;
import java.util.UUID;

public class GroupManager {
    private HashMap<UUID, Group> groups;

    public GroupManager(){
        this.groups = new HashMap<>();
    }

    public static Group createGroup(String groupName, UUID creator){
        return new Group(groupName, creator);
    }

    public void joinGroup(UUID groupId, User user){
        groups.get(groupId).addMember(user.getUserId());
    }



}

package se.chalmers.group21.spa.model.Group;

import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.model.enums.Roles;

import java.util.HashMap;
import java.util.UUID;

public class Group {
    private final UUID groupId;
    public String groupName;
    public HashMap<User, Roles> members;

    public Group(String name, User creator){
        this.members = new HashMap<>();
        members.put(creator, Roles.ADMIN);
        this.groupId = UUID.randomUUID();
        this.groupName = name;
    }
    public Group(String name){
        this.members = new HashMap<>();
        this.groupId = UUID.randomUUID();
        this.groupName = name;
    }

    public UUID getGroupCode(){
        return groupId;
    }

    public void addMember(User newMember){
        if (!members.containsKey(newMember)) {
            members.put(newMember, Roles.MEMBER);
        }

    }

    public void removeMember(User memberToRemove){
        if(members.get(memberToRemove) == Roles.ADMIN){
            for(User user : members.keySet()){
                if (members.get(user)==Roles.MEMBER){
                    changeRole(memberToRemove, user, Roles.ADMIN);
                    break;

                }
            }

        }
        members.remove(memberToRemove);
    }

    public HashMap<User, Roles> getMembers(){
        return members;
    }

    boolean isAdmin(User user){
        return members.get(user) == Roles.ADMIN;
    }

    public void changeRole(User currentUser, User userToChange, Roles role) {
        if (members.containsKey(currentUser) && members.containsKey(userToChange)) {
            if (isAdmin(currentUser)) {
                members.remove(userToChange);
                members.put(userToChange, role);
            }
        }
    }
}

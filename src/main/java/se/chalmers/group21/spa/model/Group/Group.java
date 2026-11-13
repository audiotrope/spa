package se.chalmers.group21.spa.model.Group;

import se.chalmers.group21.spa.model.Database.GroupRepo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Group{
    private final UUID groupId;
    public String groupName;
    private HashMap<UUID, Roles> members;

    //TODO filhanteringen



    public Group (String name, UUID creator){
        this.members = new HashMap<>();
        members.put(creator, Roles.ADMIN);
        this.groupId = UUID.randomUUID();
        this.groupName = name;
        try{
            GroupRepo.save(this);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public UUID getGroupCode(){
        return groupId;
    }

    void addMember(UUID newMember){
        //members.put(newMember, Roles.MEMBER);
        try{
            GroupRepo.addMember(groupId, newMember, Roles.MEMBER);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

    public void removeMember(UUID memberToRemove){
       //members.remove(memberToRemove);
        try{
            GroupRepo.removeMember(groupId, memberToRemove);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

   public Set<UUID> getMembers(){
        return members.keySet();
   }

    boolean isAdmin(UUID user){
       return members.get(user) == Roles.ADMIN;

    }
    //TODO get members

   public void changeRole(UUID currentUser, UUID userToChange, Roles role) {
       if (members.containsKey(currentUser) && members.containsKey(userToChange)) {
           if (isAdmin(currentUser)) {
               members.remove(userToChange);
               members.put(userToChange, role);
               try {
                   GroupRepo.updateRole(groupId, userToChange, role);
               } catch (SQLException e) {throw new RuntimeException(e);}
           }
           else throw new IllegalStateException("You need to be an Admin to do this");
       }
       else throw new IllegalStateException("this member is not in the group");


   }















/*
    public static void main(String[] args){


    }


 */
    }




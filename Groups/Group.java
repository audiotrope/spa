import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Group{
    private final UUID groupId;
    private String groupName;
    private HashMap<UUID, Roles> members;



    public Group (String name, UUID id, UUID creator){
        this.members = new HashMap<>();
        members.put(creator, Roles.ADMIN);
        this.groupId = id;
        this.groupName = name;

    }
    public UUID getGroupCode(){
        return groupId;
    }

   public void addMember(UUID newMember){
        members.put(newMember, Roles.MEMBER);
   }
   public boolean isAdmin(UUID user){
       //TODO
       return false;
    }

   public void changeRole(UUID currentUser, UUID userToChange, Roles role){

   }

   public void removeMember(){

   }






    }


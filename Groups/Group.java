
import java.util.HashMap;
import java.util.UUID;

public class Group{
    private final UUID groupId;
    private String groupName;
    private HashMap<UUID, Roles> members;

    //TODO filhanteringen



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

   public void removeMember(UUID memberToRemove){
        members.remove(memberToRemove);
   }

   public boolean isAdmin(UUID user){
       return members.get(user) == Roles.ADMIN;
    }

   public void changeRole(UUID currentUser, UUID userToChange, Roles role) {
       if (members.containsKey(currentUser) && members.containsKey(userToChange)) {
           if (isAdmin(currentUser)) {
               members.remove(userToChange);
               members.put(userToChange, role);
           }
           else throw new IllegalStateException("You need to be an Admin to do this");
       }
       else throw new IllegalStateException("this member is not in the group");
   }
















    public static void main(String[] args){
    User ulla = new User("ulla");
    User Anna = new User("Anna");
    Group g = ulla.createGroup("ullasGrupp");
    g.addMember(Anna.getUserId());







    }

    }


package se.chalmers.group21.spa;


import se.chalmers.group21.spa.model.Group2.Group2;
import se.chalmers.group21.spa.model.Group2.NewGroupManager;
import se.chalmers.group21.spa.model.InMemory.InMemoryGroupRepo;
import se.chalmers.group21.spa.model.InMemory.InMemoryUserRepository;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User2.User1;
import se.chalmers.group21.spa.model.User2.UserManager;

import java.sql.SQLException;

public class Main {
  public static void main(String[] args) throws SQLException {
    UserRepository userRepo = new InMemoryUserRepository();
    GroupRepository groupRepo = new InMemoryGroupRepo(userRepo);

    UserManager users = new UserManager(userRepo);
    NewGroupManager groups = new NewGroupManager(groupRepo);

    User1 anna = users.createUser("anna");
    User1 bo = users.createUser("bo");

    Group2 g = groups.createGroup("Gänget", anna);
    groups.joinGroup(g.getGroupCode(), bo);

    Group2 reloaded = groups.getGroup(g.getGroupCode());   // hämtas "från databasen"

    reloaded.getMembers().forEach((user, role) ->
            System.out.println(user.username + " " + role)
    );
    System.out.println("_____________________________");

    groups.leaveGroup(g.getGroupCode(), anna);
    Group2 reloaded2 = groups.getGroup(g.getGroupCode());   //hämtas "från databasen"

    User1 thyra = users.createUser("Thyra");
    groups.joinGroup(g.getGroupCode(), thyra);

    groups.leaveGroup(g.getGroupCode(), bo);

    reloaded2.getMembers().forEach((user, role) ->
            System.out.println(user.username + " " + role)
    );


  }
}

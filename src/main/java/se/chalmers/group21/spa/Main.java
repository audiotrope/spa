package se.chalmers.group21.spa;


import se.chalmers.group21.spa.model.Database.SupabaseClient;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC.GroupRepoAPI;
import se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC.UserRepoAPI;
import se.chalmers.group21.spa.model.User.User;

import java.util.UUID;



public class Main {
  public static void main(String[] args)  {

      SupabaseClient supabase = new SupabaseClient();
      UserRepository userRepo = new UserRepoAPI(supabase);
      GroupRepository groupRepo = new GroupRepoAPI(supabase);

      User found = userRepo.findByUsername("testanna");
      System.out.println(found.getUserId());
      Group group = new Group("testGrupp", found);
      groupRepo.save(group, found);



  }
}

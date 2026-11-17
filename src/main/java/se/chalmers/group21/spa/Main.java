package se.chalmers.group21.spa;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import se.chalmers.group21.spa.controller.Controller;
import se.chalmers.group21.spa.model.Database.Database;
import se.chalmers.group21.spa.model.Database.GroupRepo;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.Roles;
import se.chalmers.group21.spa.model.Model;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.View;

public class Main {
  public static void main(String[] args) throws IOException, SQLException {
    Database.clearAll();
    User Calle = new User("calle");
    User Ulla = new User("Ulla");
    Group g = new Group("nr1", Calle.getUserId());
    g.addMember(Ulla.getUserId());
    GroupRepo.updateRole(g.getGroupCode(), Ulla.getUserId(), Roles.ADMIN);

  }
}

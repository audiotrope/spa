package se.chalmers.group21.spa;

import java.io.IOException;

import se.chalmers.group21.spa.controller.Controller;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Model;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.View;

public class Main {
  public static void main(String[] args) throws IOException {
    User Anna = new User("anna");
    User Calle = new User("Calle");
    Group g = new Group("test", Calle.getUserId());

  }
}

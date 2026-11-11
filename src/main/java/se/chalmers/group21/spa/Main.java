package se.chalmers.group21.spa;

import java.io.IOException;

import se.chalmers.group21.spa.controller.Controller;
import se.chalmers.group21.spa.model.Model;
import se.chalmers.group21.spa.view.View;

public class Main {
  public static void main(String[] args) throws IOException {
    Model model = new Model();
    View view = new View(model);
    Controller controller = new Controller(model);
    
    System.out.println("Hello World! Press any key to exit.");
    System.in.read(); // Wait for input
  }
}

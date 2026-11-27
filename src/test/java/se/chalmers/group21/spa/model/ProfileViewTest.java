
package se.chalmers.group21.spa.model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.controller.ProfilePageControllers.ProfileController;
import se.chalmers.group21.spa.model.Database.SupabaseClient;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.GroupManager;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.Memory.InMemoryGroupRepo;
import se.chalmers.group21.spa.model.Memory.InMemoryUserRepository;
import se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC.GroupRepoAPI;
import se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC.UserRepoAPI;
import se.chalmers.group21.spa.model.User.User;

import java.util.concurrent.CountDownLatch;

public class ProfileViewTest {

    @Test
    void showMyView() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            // Skapar Test med användare från Supabase

            SupabaseClient supabase = new SupabaseClient();
            UserRepository userRepo = new InMemoryUserRepository();
            GroupRepository groupRepo = new InMemoryGroupRepo(userRepo);

            GroupManager groupManager = new GroupManager(groupRepo);
            User found = userRepo.findByUsername("Anna");




            // Skapa en testanvändare i Memory
            /*
            User testUser = new User("Anna");
            User testUser2 = new User("Bosse");
            Group testGroup = new Group("Bassarna", testUser);
            Group testGroup2 = new Group("Pansar", testUser2);
            Group testGroup3 = new Group("asdasd", testUser2);
            Group testGroup4 = new Group("Test4", testUser);
            testGroup3.addMember(testUser);
            testGroup2.addMember(testUser);
            testGroup.addMember(testUser2);
            System.out.println(testUser.groups);
             */



            // Skapa controllern
            ProfileController controller =
                    new ProfileController(found, groupManager);

            // Hämta viewn från controllern
            Scene scene = new Scene(
                    controller.getProfileView().getView()
            );
            scene.getStylesheets().add(
                    getClass().getResource("/css/theme.css").toExternalForm()
            );

            // Skapa fönstret
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Profilsida");

            stage.setOnHidden(event -> {
                latch.countDown();
            });

            stage.show();
        });

        latch.await();
    }
}

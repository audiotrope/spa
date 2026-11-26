
package se.chalmers.group21.spa.model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.controller.ProfilePageControllers.ProfileController;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.User.User;

import java.util.concurrent.CountDownLatch;

public class ProfileViewTest {

    @Test
    void showMyView() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {

            // Skapa en testanvändare
            User testUser = new User("Anna");
            User testUser2 = new User("Bosse");
            Group testGroup = new Group("Bassarna", testUser);
            testGroup.addMember(testUser2);


            // Skapa controllern
            ProfileController controller =
                    new ProfileController(testUser);

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

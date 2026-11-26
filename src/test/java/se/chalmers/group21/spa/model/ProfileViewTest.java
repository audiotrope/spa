package se.chalmers.group21.spa.model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.view.ProfileView;

import java.util.concurrent.CountDownLatch;

public class ProfileViewTest {
    @Test
    void showMyView() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {

            ProfileView view = new ProfileView();

            Stage stage = new Stage();
            stage.setScene(new Scene(view.getView()));
            stage.setTitle("Profilsida");

            stage.setOnHidden(event -> {
                latch.countDown();
            });

            stage.show();
        });

        latch.await();

    }
}

package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.ProfileView;
import se.chalmers.group21.spa.view.UserDetailsView;

public class ProfileController {

    private ProfileView profileView;
    private UserDetailsController userDetailsController;

    public ProfileController(User loggedInUser) {
        profileView = new ProfileView();
        UserDetailsView userDetailsView = new UserDetailsView();

        userDetailsController =
                new UserDetailsController(loggedInUser, profileView.getUserDetailsView());

        profileView.setUserDetailsView(userDetailsView);

        //System.out.println(userDetailsView.getChildren());

    }

    public ProfileView getProfileView() {
        return profileView;
    }
}

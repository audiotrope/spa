package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.GroupsView;
import se.chalmers.group21.spa.view.ProfileView;
import se.chalmers.group21.spa.view.UserDetailsView;

public class ProfileController {

    private ProfileView profileView;
    private UserDetailsController userDetailsController;
    private GroupDetailsController groupDetailsController;

    public ProfileController(User loggedInUser) {
        profileView = new ProfileView();

        UserDetailsView userDetailsView = new UserDetailsView();
        GroupsView groupDetails = new GroupsView();

        userDetailsController =
                new UserDetailsController(loggedInUser, profileView.getUserDetailsView());
        profileView.setUserDetailsView(userDetailsView);

        groupDetailsController = new GroupDetailsController(
                loggedInUser, profileView.getGroupsView()
        );

        //System.out.println(userDetailsView.getChildren());

    }

    public ProfileView getProfileView() {
        return profileView;
    }
}

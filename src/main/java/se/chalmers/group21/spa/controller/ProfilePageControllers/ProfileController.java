package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.Group.GroupManager;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.GroupsView;
import se.chalmers.group21.spa.view.ProfileView;
import se.chalmers.group21.spa.view.UserDetailsView;

public class ProfileController {

    private final ProfileView profileView;
    private final UserDetailsController userDetailsController;
    private final GroupDetailsController groupDetailsController;
    private final GroupManager groupManager;

    public ProfileController(User loggedInUser,GroupManager groupManager) {
        profileView = new ProfileView();
        this.groupManager = groupManager;

        UserDetailsView userDetailsView = new UserDetailsView();
        GroupsView groupDetails = new GroupsView();

        userDetailsController = new UserDetailsController(
                loggedInUser,
                profileView.getUserDetailsView()
        );

        //profileView.setUserDetailsView(userDetailsView);

        groupDetailsController = new GroupDetailsController(
                loggedInUser,
                profileView.getGroupsView(),
                groupManager
        );

        //System.out.println(userDetailsView.getChildren());

    }

    public ProfileView getProfileView() {
        return profileView;
    }
}

package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.UserDetailsView;

public class UserDetailsController {
    private User user;
    private UserDetailsView view;

    public UserDetailsController(User user, UserDetailsView view){
        this.view = view;
        this.user = user;

        updateView();

    }

    private void updateView(){
        view.setUserDetails(
                user.getUsername(),
                user.getUserId().toString()
        );


    }

}

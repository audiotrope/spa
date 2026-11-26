package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.GroupsView;

public class GroupDetailsController {
    private GroupsView view;
    private User user;

    public GroupDetailsController(User user,  GroupsView view){

        this.view = view;
        this.user = user;

        updateView();
    }

    public void updateView(){
        for (Group group: user.groups){
            view.setGroupDetails(group.groupName, group.members.get(user).toString());
        }
    }
}

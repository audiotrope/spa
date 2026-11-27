package se.chalmers.group21.spa.controller.ProfilePageControllers;

import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.GroupManager;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.view.GroupsView;

public class GroupDetailsController {
    private GroupsView view;
    private User user;
    private GroupManager manager;

    public GroupDetailsController(User user,  GroupsView view, GroupManager manager){

        this.view = view;
        this.user = user;
        this.manager = manager;
        updateView();
    }

    public void updateView(){

        for (Group group :manager.getGroupsForUser(user).keySet()){
            view.setGroupDetails(group.groupName, group.members.get(user).toString());
        }
        /*
        for (Group group: user.groups){
            view.setGroupDetails(group.groupName, group.members.get(user).toString());
        }
         */
    }
}

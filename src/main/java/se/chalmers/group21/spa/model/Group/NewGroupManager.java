package se.chalmers.group21.spa.model.Group;

import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.User.User;
import se.chalmers.group21.spa.model.enums.Roles;

import java.sql.SQLException;
import java.util.UUID;

public class NewGroupManager {
    private final GroupRepository repo;

    public NewGroupManager(GroupRepository repo) {
        this.repo = repo;
    }

    public Group createGroup(String name, User creator ){
        Group group = new Group(name, creator);
        repo.save(group);
        return group;
    }

    public Group getGroup(UUID groupId){
        Group group = repo.findGroupById(groupId);
        if (group == null){
            throw new IllegalArgumentException("ingen grupp med id" + groupId);
        }
        return group;
    }

    public void joinGroup(UUID groupId, User user) throws SQLException {
        Group group = getGroup(groupId);
        group.addMember(user);
        repo.addMember(groupId, user);
    }

    public void leaveGroup(UUID groupId, User user) throws SQLException {
        Group group = getGroup(groupId);
        group.removeMember(user);
        repo.removeMember(groupId, user);
    }

    public void updateRole(UUID groupId, User userThatChanges, User userToChange, Roles role) throws SQLException{
        Group group = getGroup(groupId);
        group.changeRole(userThatChanges, userToChange, role);
        repo.changeRole(groupId, userThatChanges,userToChange, role);
    }
}

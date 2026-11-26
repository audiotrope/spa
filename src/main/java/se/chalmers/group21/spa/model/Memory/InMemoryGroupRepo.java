package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.enums.Roles;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User.User;

import java.util.*;

public class InMemoryGroupRepo implements GroupRepository{
    public HashSet<Group> groups;

    public InMemoryGroupRepo (UserRepository userRepo){
        this.groups = new HashSet<>();


    }

    @Override
    public void save(Group group) {
        if (findGroupById(group.getGroupCode()) != null){
            throw new IllegalArgumentException("group already exists" + group.groupName);
        }
        groups.add(group);
    }


    @Override
    public void addMember(UUID groupId, User user) {
        Group group = findGroupById(groupId);
        group.addMember(user);
    }

    @Override
    public void removeMember(UUID groupId, User UserToRemove) {
        Group group = findGroupById(groupId);
        group.removeMember(UserToRemove);
    }

    @Override
    public Group findGroupById(UUID id) {
        for (Group g : groups) {
            if (g.getGroupCode() == id)
                return g;
        }
        return null;
    }

    @Override
    public void changeRole(UUID groupId, User userThatChanges, User userToChange, Roles role) {
        Group group = findGroupById(groupId);
        group.changeRole(userThatChanges, userToChange, role);
    }
}
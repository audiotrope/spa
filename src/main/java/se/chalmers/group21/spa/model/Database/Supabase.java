package se.chalmers.group21.spa.model.Database;

import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.Roles;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User.User;

import java.util.UUID;

public class Supabase implements GroupRepository, UserRepository {
    @Override
    public void save(Group group, User creator) {

    }

    @Override
    public void addMember(UUID groupId, User userId) {

    }

    @Override
    public void removeMember(UUID groupId, User UserToRemove) {

    }

    @Override
    public Group findGroupById(UUID id) {
        return null;
    }

    @Override
    public void changeRole(UUID groupId, User userThatChanges, User UserToChange, Roles role) {

    }

    @Override
    public User findUserById(UUID id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void saveUser(User User) {

    }

}

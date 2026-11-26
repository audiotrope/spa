package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.enums.Roles;
import se.chalmers.group21.spa.model.User.User;

import java.sql.SQLException;
import java.util.UUID;

public interface GroupRepository {
     void save(Group group);
     void addMember(UUID groupId, User user) throws SQLException;
     void removeMember(UUID groupId, User UserToRemove)throws SQLException;
     Group findGroupById(UUID id);
     void changeRole(UUID groupId, User userThatChanges, User UserToChange, Roles role) throws SQLException;
}

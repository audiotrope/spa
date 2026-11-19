package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.Group2.Group2;
import se.chalmers.group21.spa.model.Group.Roles;
import se.chalmers.group21.spa.model.User2.User1;

import java.sql.SQLException;
import java.util.UUID;

public interface GroupRepository {
     void save(Group2 group);
     void addMember(UUID groupId, User1 userId) throws SQLException;
     void removeMember(UUID groupId, User1 UserToRemove)throws SQLException;
     Group2 findGroupById(UUID id);
     void changeRole(UUID groupId, User1 userThatChanges, User1 UserToChange, Roles role) throws SQLException;
}

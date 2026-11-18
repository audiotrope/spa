package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.Group2.Group2;
import se.chalmers.group21.spa.model.Group.Roles;

import java.sql.SQLException;
import java.util.UUID;

public interface GroupRepository {
     void save(Group2 group);
     void addMember(UUID groupId, UUID userId) throws SQLException;
     void removeMember(UUID groupId, UUID UserToRemove)throws SQLException;
     Group2 findGroupById(UUID id);
     void changeRole(UUID groupId, UUID userThatChanges, UUID UserToChange, Roles role) throws SQLException;
}

package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.Group2.Group2;
import se.chalmers.group21.spa.model.Group.Roles;

import java.util.UUID;

public interface GroupRepository {
    public void save(Group2 group);
    public void addMember(UUID groupId, UUID userId);
    public void removeMember(UUID groupId, UUID UserToRemove);
    public Group2 findGroupById(UUID id);
    public void changeRole(UUID groupId, UUID userThatChanges, UUID UserToChange, Roles role);
}

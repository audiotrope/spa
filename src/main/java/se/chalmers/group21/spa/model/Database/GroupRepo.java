
package se.chalmers.group21.spa.model.Database;
import se.chalmers.group21.spa.model.Group.Roles;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;



public class GroupRepo implements GroupRepository {



    @Override
    public void save(Group group) {
        String sql = "INSERT INTO groups (id, name) VALUES (?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, group.getGroupCode());
            ps.setString(2, group.groupName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMember(UUID groupId, User userId) throws SQLException {
        String sql = "INSERT INTO group_members ( group_id, user_id, role ) VALUES (?,?,?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, groupId);
            ps.setObject(2, userId);
            ps.setObject(3, Roles.MEMBER);
            ps.executeUpdate();
        }
    }
    @Override
    public void removeMember(UUID groupId, User userId) throws SQLException{
        String sql = "DELETE FROM group_members WHERE group_id = ? AND user_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, groupId);
            ps.setObject(2, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public Group findGroupById(UUID id) {
        String sql = "SELECT id, name FROM groups WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Group(rs.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeRole(UUID groupId, User userThatChanges, User userToChange, Roles role)throws SQLException {
        String sql = "UPDATE group_members SET role = ? WHERE group_id = ? AND user_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, role.name());
            ps.setObject(2, groupId);
            ps.setObject(3, userToChange);
            ps.executeUpdate();
        }
    }


}

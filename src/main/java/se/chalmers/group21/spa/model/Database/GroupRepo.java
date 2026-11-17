package se.chalmers.group21.spa.model.Database;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;



public class GroupRepo {
    public static void save(Group group) {
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


    public static void addMember(UUID groupId, UUID userId, Roles role) throws SQLException{
        String sql = "INSERT INTO group_members ( group_id, user_id, role ) VALUES (?,?,?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, groupId);
            ps.setObject(2, userId);
            ps.setObject(3, role.name());
            ps.executeUpdate();
        }
    }


    public static void removeMember(UUID groupId, UUID userId) throws SQLException{
        String sql = "DELETE FROM group_members WHERE group_id = ? AND user_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, groupId);
            ps.setObject(2, userId);
            ps.executeUpdate();
        }
    }
    public static Group findById(UUID id) {
        String sql = "SELECT id, name FROM groups WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Group(rs.getString("name"), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateRole(UUID groupId, UUID userId, Roles role) throws SQLException{
        String sql = "UPDATE group_members SET role = ? WHERE group_id = ? AND user_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, role.name());
            ps.setObject(2, groupId);
            ps.setObject(3, userId);
            ps.executeUpdate();
        }
    }
    public static Group getGroup(){
        return null;
    };



}

package se.chalmers.group21.spa.model.Database;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static org.postgresql.core.Oid.UUID;

public class GroupRepo {
    public static void save (Group group) throws SQLException{
        try(Connection conn = Database.connect()){
            conn.setAutoCommit(false);
            try{
                try(PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO groups (id,name) VALUES (?,?)")) {
                    ps.setObject(1, group.getGroupCode());
                    ps.setObject(2, group.groupName);
                    ps.executeUpdate();
                }
                try(PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO group_members (group_id, user_id) VALUES  (?,?)")) {
                    for (UUID member : group.getMembers()) {
                        ps.setObject(1, group.getGroupCode());
                        ps.setObject(2, member);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                conn.commit();
            }catch (SQLException e){
                conn.rollback();
                throw e;
            }
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



}

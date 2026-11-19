package se.chalmers.group21.spa.model.Database;

import se.chalmers.group21.spa.model.User2.User1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepo {
    public static void save(User1 user) throws SQLException{
        String sql = "INSERT INTO users (id, username) VALUES(?,?)";
        try(Connection conn = Database.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, user.getUserId());
            ps.setObject(2, user.username);
            ps.executeUpdate();
        }
    }

    public static User1 findById(UUID id) throws SQLException{
        String sql = "SELECT id, username FROM users where id = ?";
        try(Connection conn = Database.connect();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();                                                                       //
            if (rs.next()) {                                                                                        // AI
                return new User1(rs.getObject("id", UUID.class), rs.getString("username"));   //
            }
            return null;
        }
    }



}

/*
package se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC;

import se.chalmers.group21.spa.model.Database.Database;
import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepoJDBC implements UserRepository {

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (id, username) VALUES(?,?)";
        try(Connection conn = Database.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, user.getUserId());
            ps.setObject(2, user.username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserById(UUID userId) {
        String sql = "SELECT id, username FROM users where id = ?";
        try(Connection conn = Database.connect();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setObject(1, userId);
            ResultSet rs = ps.executeQuery();                                                                       //
            if (rs.next()) {                                                                                        // AI
                return new User(rs.getObject("id", UUID.class), rs.getString("username"));   //
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}


 */
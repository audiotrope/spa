package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.User.User;

import java.util.UUID;

public interface UserRepository {
    public void saveUser(User User);
    public User findUserById(UUID userId);
    public User findByUsername(String username);
}

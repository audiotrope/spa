package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.Group2.Group2;
import se.chalmers.group21.spa.model.User2.User1;

import java.util.UUID;

public interface UserRepository {
    public void saveUser(User1 User);
    public User1 findUserById(UUID userId);
    public User1 findByUsername(String username);
}

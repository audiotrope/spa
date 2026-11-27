package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.Interfaces.UserRepository;
import se.chalmers.group21.spa.model.User.User;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public void saveUser(User user) {
        if (findByUsername(user.getUsername()) != null) {
            throw new IllegalStateException("duplicate key: " + user.getUsername());
        }
        users.put(user.getUserId(), user);
    }

    @Override
    public User findUserById(UUID id) { return users.get(id); }

    @Override
    public User findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}
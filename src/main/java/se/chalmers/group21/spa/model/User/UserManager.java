package se.chalmers.group21.spa.model.User;


import se.chalmers.group21.spa.model.Interfaces.UserRepository;

import java.util.UUID;

public class UserManager {
    private final UserRepository repo;

    public UserManager(UserRepository repo){
        this.repo = repo;
    }
    public User createUser(String username){
        if (repo.findByUsername(username) != null) {
            throw new IllegalStateException("Användarnamnet är upptaget");
        }
        User user = new User(username);
        repo.saveUser(user);
        return user;
    }
    public User findByUsername(String username){
        return repo.findByUsername(username);
    }
    public User getUser(UUID UserId){
        User user = repo.findUserById(UserId);
        if (user == null)throw new IllegalArgumentException("Denna användaren finns inte");
        return user;
    }
}

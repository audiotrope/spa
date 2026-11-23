package se.chalmers.group21.spa.model;

import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.User.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {

    @Test
    void constructorSetsUsername() {
        User user = new User("anna");
        assertEquals("anna", user.username);
    }

    @Test
    void differentUserSameName(){
        User a = new User("a");
        User b = new User("b");
        assertNotEquals(a.getUserId(), b.getUserId());
    }

    @Test
    void differentUsersAreNotTheSame(){
        User a = new User("a");
        User b = new User("b");
        assertNotEquals(a, b);
    }
}

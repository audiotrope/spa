package se.chalmers.group21.spa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.enums.Roles;
import se.chalmers.group21.spa.model.User.User;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    private Group group;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("TestUser");
        group = new Group("testGroup",testUser);
    }

    @Test
    void groupName(){
        assertEquals("testGroup", group.groupName);
    }

    @Test
    void addAndRemoveMembers(){
        User testUser2 = new User("TestUser2");
        group.addMember(testUser2);
        assertTrue(group.members.containsKey(testUser2));
        group.removeMember(testUser2);
        assertFalse(group.members.containsKey(testUser2));

    }
    @Test
    void memberRoles(){
        User testUser3 = new User("TestUser3");
        group.addMember(testUser3);
        assertSame(Roles.MEMBER, group.members.get(testUser3));
        assertSame(Roles.ADMIN, group.members.get(testUser));
    }

    @Test
    void newAdminWhenAdminLeaves(){
        User testUser4 = new User("TestUser4");
        Group testGroup2 = new Group("testGrupp2", testUser4);
        User testUser5 = new User("TestUser5");
        testGroup2.addMember(testUser5);
        testGroup2.removeMember(testUser4);
        assertSame(Roles.ADMIN, testGroup2.members.get(testUser5));
    }
    @Test
    void changeRole(){
        User testUser6 = new User("testUser6");
        group.addMember(testUser6);
        group.changeRole(testUser, testUser6, Roles.ADMIN);
        assertSame(Roles.ADMIN, group.members.get(testUser6));
    }

    @Test
    void addingSameMemberTwice(){
        group.addMember(testUser);
        int count = 1;
        for (User user : group.members.keySet()){
            if (user == testUser) count--;
        }
        assertEquals(0, count);
    }

}

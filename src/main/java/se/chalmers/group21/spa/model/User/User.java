package se.chalmers.group21.spa.model.User;

import se.chalmers.group21.spa.model.Database.UserRepo;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.GroupManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


public class User {
    public final String username;
    private final UUID userId;
    private ArrayList<String> courses;
    public ArrayList<Group> groups;

    //TODO
    //Todos todos;
    //Schedule schedule;

    public User (UUID id, String username){
        this.userId = id;
        this.username = username;
    }

    public User (String username){
        this.userId = UUID.randomUUID();
        this.username = username;
        this.courses = new ArrayList<>();
        this.groups = new ArrayList<>();
        try{
            UserRepo.save(this);
        } catch(SQLException e){throw new RuntimeException(e);
        }
    }

    public void leaveGroup(Group groupId){
        groups.remove(groupId);
        groupId.removeMember(userId);

    }
    public void createGroup(String groupname){
        GroupManager.createGroup(groupname, userId);

    }

    public void removeCourse (String course){
        courses.remove(course);
    }
    public void addCourse (String courseName){
        courses.add(courseName);
    }
    public UUID getUserId(){
        return userId;
    }

    //TODO grades
}

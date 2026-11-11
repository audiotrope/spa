package se.chalmers.group21.spa.model.User;

import se.chalmers.group21.spa.model.Group.Group;

import java.util.ArrayList;
import java.util.UUID;


public class User {
    private final String username;
    private final UUID userId;
    private ArrayList<String> courses;
    public ArrayList<Group> groups;

    //Todos todos;
    //Schedule schedule;


    public User (String username){
        this.userId = UUID.randomUUID();
        this.username = username;

        this.courses = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void leaveGroup(Group groupId){
        groups.remove(groupId);
        groupId.removeMember(userId);

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

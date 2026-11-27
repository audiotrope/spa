package se.chalmers.group21.spa.model.User;


import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.entities.TaskAssignment;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


public class User {
    private final String username;
    private final UUID userId;
    private ArrayList<String> courses;
    public ArrayList<Group> groups;

    //TODO
    //Todos todos;
    //Schedule schedule;

    public User(UUID id, String username){
        this.userId = id;
        this.username = username;
    }

    public User(String username){
        this.userId = UUID.randomUUID();
        this.username = username;
        this.courses = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void leaveGroup(Group group){
        groups.remove(group);

    }
    public void joinGroup(Group group){
        group.addMember(this);
    }

    public void createGroup(String groupname){
        Group group = new Group(groupname, this);
        groups.add(group);

    }
    public String getUsername(){
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        return Objects.equals(userId, other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
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

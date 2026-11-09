import java.util.ArrayList;
import java.util.UUID;


public class User {
    private final String username;
    private final UUID userId;
    private ArrayList<String> courses;
    private ArrayList<Group> groups;

    //Todos todos;
    //Schedule schedule;
    public User (String username){
        this.userId = UUID.randomUUID();
        this.username = username;
        this.courses = new ArrayList<>();
        this.groups = new ArrayList<>();
    }
    public void joinGroup(UUID code){
        //TODO
    }
    public void leaveGroup(Group groupName){
        groups.remove(groupName);

    }
    public void createGroup(String groupName){
        groups.add(new Group(groupName, UUID.randomUUID(), userId));
    }
    public void removeCourse (String course){
        courses.remove(course);
    }
    public void addCourse (String courseName){
        courses.add(courseName);
    }
}

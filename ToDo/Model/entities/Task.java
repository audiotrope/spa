package Model.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private String status;
    //private Set<String> users;
    private UUID id;
    private LocalDate deadline;

    public Task(UUID id, String title, String description, LocalDate deadline){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "Undoing";
        this.deadline = deadline;
        //this.users = new Set<String>();
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void ChangeStatus(String status){
        this.status = status;
    }

    public String getTitle(){
        return title;
    }

    public String getStatus(){
        return status;
    }

    public LocalDate getDeadline(){
        return deadline;
    }

    //public void assignUser(User user){
   //     users.add(user);
    //}
}

package Model.entities;

import Model.enums.Status;
import Model.enums.TaskType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private Status status;
    //private Set<String> users;
    private final UUID id;
    private LocalDate deadline;
    private Set<TaskAssignment> assignments;
    private TaskType type;
    private UUID boardId;
    private UUID listId;
    private UUID ownerId;

    public Task(String title, String description, LocalDate deadline, TaskType type, UUID ownerId, UUID boardId, UUID listId){
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
        this.deadline = deadline;
        this.type  = type;
        this.ownerId = ownerId;
        this.boardId = boardId;
        this.listId = listId;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void ChangeStatus(Status status){
        this.status = status;
    }

    public String getTitle(){
        return title;
    }

    public Status getStatus(){
        return status;
    }

    public LocalDate getDeadline(){
        return deadline;
    }

    public void assignUser(UUID userId){
       if (type != TaskType.GROUP){
            throw new IllegalStateException(
                    "Only group tasks can have assignments"
            );
       }
       if (userId == null){
           throw new IllegalStateException(
                   "User ID can not bu null"
           );
       }
        // här ska den på något sätt kalla på TaskAssignment som hanterar alla assignments
        // för att jobba enligt SOLID
    }

    public void unassignUser(UUID userId){

    }
}

package se.chalmers.group21.spa.model.entities;


import se.chalmers.group21.spa.model.enums.Status;
import se.chalmers.group21.spa.model.enums.TaskType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private Status status;
    private final UUID id;
    private LocalDate deadline;
    private Set<TaskAssignment> assignments;
    private TaskType type;
    private final UUID boardId;
    private UUID listId;
    private final UUID ownerId;

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
        this.assignments = new HashSet<TaskAssignment>();
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeStatus(Status status){
        this.status = status;
    }

    public void changeDeadline(LocalDate deadline){
        this.deadline = deadline;
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
    public UUID getId(){
        return id;
    }
    public UUID getOwnerId(){return ownerId;}
    public UUID getListId(){
        return listId;
    }

    public Set<TaskAssignment> getAssignments() {
        return assignments;
    }

    public void changeListId(UUID listId){
        this.listId = listId;
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
       assignments.add(new TaskAssignment(userId));
    }

    public void unassignUser(UUID userId){
        assignments.removeIf(
                user -> user.getUserId().equals(userId)
        );
    }
}

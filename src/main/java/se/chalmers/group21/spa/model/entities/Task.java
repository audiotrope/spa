package se.chalmers.group21.spa.model.entities;

import se.chalmers.group21.spa.model.enums.Status;
import se.chalmers.group21.spa.model.enums.TaskType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents an individual task, it can be either personal och group-related.It stores information sush
 * as title, description, status, deadline and tasktype.
 * For personal tasks: it stores the unique ID of the user who owns the task.
 * For group tasks: it stores the board and list IDs and managers its assignments.
 * A Task handles changes to its own state, such as title, status, deadline and description. It also determines whether
 * a task is overdue or approaching its deadline (for dynamic change without users input).
 *
 * Methods genereated by AI: assignUser(UUID userId),
 * createPersonal(String title, String description, LocalDate deadline, UUID ownerId),
 *createGroup(String title, String description, LocalDate deadline, UUID boardId, UUID listId, Set<UUID> assignedUserIds)
 * @author Thyra Öfverström
 */

public class Task {
    private String title;
    private String description;
    private Status status;
    private final UUID id;
    private LocalDate deadline;
    private Set<TaskAssignment> assignments;
    private final TaskType type;
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
        this.assignments = new HashSet<>();
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

    public boolean overDeadline(){
        return deadline != null && !deadline.isAfter(LocalDate.now());
    }

    public boolean isDeadlineSoon(){
        return deadline != null && deadline.isAfter(LocalDate.now()) && !deadline.isAfter(LocalDate.now().plusDays(3));
    }

    public Set<TaskAssignment> getAssignments() {
        return Set.copyOf(assignments);
    }
    public void changeListId(UUID listId){
        this.listId = listId;
    }

    //AI
    public void assignUser(UUID userId){
       if (type != TaskType.GROUP){
            throw new IllegalArgumentException(
                    "Only group tasks can have assignments"
            );
       }
       if (userId == null){
           throw new IllegalArgumentException(
                   "User ID can not be null"
           );
       }
       assignments.add(new TaskAssignment(userId));
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void unassignUser(UUID userId){
        assignments.removeIf(user -> user.getUserId().equals(userId));
    }

    //AI
    public static Task createPersonal(String title, String description, LocalDate deadline, UUID ownerId) {
        return new Task(title, description, deadline, TaskType.PERSONAL, ownerId, null, null);
    }

    //AI
    public static Task createGroup(String title, String description, LocalDate deadline, UUID boardId, UUID listId, Set<UUID> assignedUserIds) {
        Task task = new Task(title, description, deadline, TaskType.GROUP, null, boardId, listId);
        if (assignedUserIds != null) {
            for (UUID userId : assignedUserIds) {
                task.assignUser(userId);
            }
        }
        return task;
    }
}

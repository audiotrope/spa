package se.chalmers.group21.spa.model.entities;

import java.util.ArrayList;
import java.util.UUID;

public class Board {
    private ArrayList<TaskList> taskLists;
    private final UUID id;
    private final UUID groupId;
    //private String title;

   public Board(UUID groupId) {
            this.groupId = groupId;
            this.id = UUID.randomUUID();
            this.taskLists = new ArrayList<>();
        }

    public void addList(TaskList list){
        taskLists.add(list);
    }
    public void removeList(UUID taskListId){
        taskLists.removeIf(
                list -> list.getId().equals(taskListId)
        );
    }
    public UUID getId(){
        return id;
    }
    public UUID getGroupId(){
        return groupId;
    }
    public ArrayList<TaskList> getTaskLists(){
        return taskLists;
    }
    public void moveTask(Task task, UUID targetListId) { //AI

        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (!this.id.equals(task.getBoardId())) {
            throw new IllegalArgumentException(
                    "Task does not belong to this board");
        }

        if (!containsList(targetListId)) {
            throw new IllegalArgumentException(
                    "Target list does not belong to this board");
        }

        task.changeListId(targetListId);
    }

    public boolean containsList(UUID listId) {
        return taskLists.stream()
                .anyMatch(list -> list.getId().equals(listId));
    }
    public void reorderTaskList(UUID listId, int newPosition) { //AI
        if (newPosition < 0 || newPosition >= taskLists.size()) {
            throw new IllegalArgumentException("Invalid position");
        }

        TaskList taskList = taskLists.stream()
                .filter(list -> list.getId().equals(listId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("TaskList not found"));

        taskLists.remove(taskList);
        taskLists.add(newPosition, taskList);

        // Uppdatera positionen för alla listor
        for (int i = 0; i < taskLists.size(); i++) {
            taskLists.get(i).reorder(i);
        }
    }
}

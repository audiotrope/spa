package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Board;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.entities.TaskAssignment;

import java.util.*;

public class InMemoryTask implements TaskRepository {
    private Map<UUID, Task> tasks  = new HashMap<>();

    @Override
    public void save(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Task findById(UUID id) {
        return tasks.get(id);
    }

    @Override
    public void delete(UUID id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> findPersonal(UUID userId) { // kanske ska vara ett set istället för list?
        List<Task> personal = new ArrayList<>();
        for(Task task: tasks.values()){
            if (task.getOwnerId() == userId){
                personal.add(task);
            }
            for(TaskAssignment user: task.getAssignments()){
                if(user.getUserId() == userId){
                    personal.add(task);
                }
            }
        }
        return personal;
    }

    @Override
    public List<Task> findAssignedGroup(UUID userId) { // vet inte riktigt denna ännu
        return List.of();
    }
}

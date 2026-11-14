package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Task;
import se.chalmers.group21.spa.model.enums.TaskType;

import java.util.*;

public class InMemoryTask implements TaskRepository {
    private Map<UUID, Task> tasks  = new HashMap<>();

    @Override
    public void save(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Optional<Task> findById(UUID id) { //AI
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public void delete(UUID id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> findByListId(UUID listId) {
        return tasks.values().stream()
                .filter(task -> listId.equals(task.getListId()))
                .toList();
    }

    @Override
    public List<Task> findPersonal(UUID userId) { //AI
        return tasks.values().stream()
                .filter(task -> task.getType() == TaskType.PERSONAL)
                .filter(task -> userId.equals(task.getOwnerId()))
                .toList();
    }

    @Override
    public List<Task> findAssignedGroup(UUID userId) { //AI
        return tasks.values().stream()
                .filter(task -> task.getType() == TaskType.GROUP)
                .filter(task -> task.getAssignments().stream()
                        .anyMatch(assignment ->
                                userId.equals(assignment.getUserId())))
                .toList();
    }
}

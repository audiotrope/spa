package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {
    void save(Task task);
    Task findById(UUID id);
    void delete(UUID id);

    List<Task> findPersonal(UUID userId);
    List<Task> findAssignedGroup(UUID userId);
}

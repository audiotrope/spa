package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This interface defines the operations used to store, retrive and delete tasks. It provides methods for finding
 * tasks by ID, list, owner or assigned user. It separates task management from the underlying data storage
 * implementation
 * @author Thyra Öfverström
 */

public interface TaskRepository {
    void save(Task task);
    Optional<Task> findById(UUID id);
    void delete(UUID id);
    List<Task> findByListId(UUID listId);
    List<Task> findPersonal(UUID userId);
    List<Task> findAssignedGroup(UUID userId);
}

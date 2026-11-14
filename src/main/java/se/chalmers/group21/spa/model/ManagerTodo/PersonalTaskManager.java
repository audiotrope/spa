package se.chalmers.group21.spa.model.ManagerTodo;

import se.chalmers.group21.spa.model.Interfaces.TaskRepository;
import se.chalmers.group21.spa.model.entities.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PersonalTaskManager {

    private TaskRepository taskRepository;

    public PersonalTaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getPersonalTasks(UUID userId) {

        List<Task> personalTasks =
                taskRepository.findPersonal(userId);

        List<Task> assignedTasks =
                taskRepository.findAssignedGroup(userId);

        // Samla båda typerna i samma lista.
        // LinkedHashMap förhindrar dubbletter baserat på taskens ID.
        Map<UUID, Task> tasks = new LinkedHashMap<>();

        for (Task task : personalTasks) {
            tasks.put(task.getId(), task);
        }

        for (Task task : assignedTasks) {
            tasks.put(task.getId(), task);
        }

        return new ArrayList<>(tasks.values());
    }
}
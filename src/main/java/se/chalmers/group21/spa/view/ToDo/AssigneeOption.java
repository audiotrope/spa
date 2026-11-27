package se.chalmers.group21.spa.view.ToDo;

import java.util.UUID;

/**
 * Represents one row in the list of users in the edit panel (TaskDetailView). It is created by
 * TaskCardController from the members of the group, and tells the view which users can be assigned to the task
 * and which of them already are.
 *
 * @param id       unique ID of the user
 * @param name     name shown next to the checkbox
 * @param selected true if the user is currently assigned to the task
 * @author Thyra Öfverström
 */
public record AssigneeOption(UUID id, String name, boolean selected) {

}

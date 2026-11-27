package se.chalmers.group21.spa.view.ToDo;

import java.time.LocalDate;

/**
 * Read-only start values for the edit panel (TaskDetailView). It is created by TaskCardController from a Task when
 * a card is clicked, so the panel can fill in its fields without depending on the Task entity.
 *
 * @param title       current title of the task
 * @param description current description, may be empty
 * @param deadline    current deadline, null if the task has none
 * @author Thyra Öfverström
 */

public record TaskDetailViewModel(String title, String description, LocalDate deadline) {

}

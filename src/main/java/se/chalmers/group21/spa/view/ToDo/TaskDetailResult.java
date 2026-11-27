package se.chalmers.group21.spa.view.ToDo;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * The values a user has entered in the edit panel (TaskDetailView) when pressing Save. It is passed from the view
 * to TaskCardController, which forwards it to TaskManager. Validation is not done here, it is handled by the model.
 *
 * @param title       new title entered by the user
 * @param description new description entered by the user
 * @param deadline    new deadline, null if the user cleared it
 * @param assigneeIds IDs of all users that should be assigned to the task after saving
 * @author Thyra Öfverström
 */

public record TaskDetailResult(String title, String description, LocalDate deadline, Set<UUID> assigneeIds) { }

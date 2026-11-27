package se.chalmers.group21.spa.view.ToDo;

import se.chalmers.group21.spa.model.enums.DeadlineState;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Read-only data needed to display a single task card. It is created by TaskCardController from a Task and passed
 * to TaskCardView, so the view never has to know about the Task entity and cannot modify the model directly.
 * The deadline state is calculated in the controller, so the view only decides how to style it.
 *
 * @param id            unique ID of the task, passed back when the card is clicked
 * @param title         title shown on the card
 * @param description   optional description, the card hides it when empty
 * @param deadline      optional deadline, null if the task has none
 * @param deadlineState how urgent the deadline is, used to choose the badge style
 * @param assigneeCount number of users assigned to the task
 * @author Thyra Öfverström
 */

public record TaskCardViewModel(
        UUID id,
        String title,
        String description,
        LocalDate deadline,
        DeadlineState deadlineState,
        int assigneeCount
) {
}
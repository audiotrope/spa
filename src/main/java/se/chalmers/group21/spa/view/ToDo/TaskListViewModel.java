package se.chalmers.group21.spa.view.ToDo;

/**
 * Read-only data needed to display a task list (a column) on the board. It is created by TaskListController from a
 * TaskList, so the view does not depend on the TaskList entity.
 *
 * @param title    name of the list, shown in the header
 * @param position position of the list on the board, used to choose the column's colour
 * @author Thyra Öfverström
 */
public record TaskListViewModel(String title, int position) { }

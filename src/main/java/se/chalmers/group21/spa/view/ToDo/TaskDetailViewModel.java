package se.chalmers.group21.spa.view.ToDo;

import java.time.LocalDate;

public record TaskDetailViewModel(String title, String description, LocalDate deadline) {

}

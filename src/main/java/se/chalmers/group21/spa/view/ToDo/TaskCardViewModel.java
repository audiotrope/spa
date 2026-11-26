package se.chalmers.group21.spa.view.ToDo;

import se.chalmers.group21.spa.model.enums.DeadlineState;

import java.time.LocalDate;
import java.util.UUID;

public record TaskCardViewModel(
        UUID id,
        String title,
        String description,
        LocalDate deadline,
        DeadlineState deadlineState,
        int assigneeCount
) {
}
package se.chalmers.group21.spa.view.ToDo;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record TaskDetailResult(String title, String description, LocalDate deadline, Set<UUID> assigneeIds) { }

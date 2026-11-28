package se.chalmers.group21.spa.model.Interfaces;

import java.util.Optional;
import java.util.UUID;

import se.chalmers.group21.spa.model.schedule.UserSchedule;

public interface UserScheduleRepository {

    void save(UserSchedule schedule);

    Optional<UserSchedule> findById(UUID id);

    Optional<UserSchedule> findByUserId(UUID userId);

    void delete(UUID id);
}
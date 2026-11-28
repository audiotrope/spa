package se.chalmers.group21.spa.model.Interfaces;

import java.util.Optional;
import java.util.UUID;

import se.chalmers.group21.spa.model.schedule.GroupSchedule;

public interface GroupScheduleRepository {

    void save(GroupSchedule schedule);

    Optional<GroupSchedule> findById(UUID id);

    Optional<GroupSchedule> findByGroupId(UUID groupId);

    void delete(UUID id);
}
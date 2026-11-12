package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.entities.Board;

import java.util.UUID;

public interface BoardRepository {
    void save(Board board);
    void delete(UUID id);
    Board findById(UUID id);
    Board findByGroup(UUID groupId);
}

package se.chalmers.group21.spa.model.Interfaces;

import se.chalmers.group21.spa.model.entities.Board;

import java.util.Optional;
import java.util.UUID;

/**
 * This interface defines the operations used to store, retrieve and delete boards. It provides methods for finding
 * boards by their ID or associated group. It separates board management from the underlying data storage implementation.
 * @author Thyra Öfverström
 */

public interface BoardRepository {
    void save(Board board);
    Optional<Board> findById(UUID id);
    Optional<Board> findByGroupId(UUID groupId);
}

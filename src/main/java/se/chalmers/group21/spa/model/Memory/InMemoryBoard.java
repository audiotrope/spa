package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.entities.Board;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryBoard implements BoardRepository {
    private Map<UUID, Board> boards  = new HashMap<>();

    @Override
    public void save(Board board) {
        boards.put(board.getId(), board);
    }

    @Override
    public Optional<Board> findById(UUID id) {
        return Optional.ofNullable(boards.get(id)); //AI ish, samma som InMemoryTask
    }

    @Override
    public Optional<Board> findByGroupId(UUID groupId) {
        return boards.values().stream()
                .filter(board -> board.getGroupId().equals(groupId))
                .findFirst();
    }

}

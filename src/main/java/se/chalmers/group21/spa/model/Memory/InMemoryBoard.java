package se.chalmers.group21.spa.model.Memory;

import se.chalmers.group21.spa.model.Interfaces.BoardRepository;
import se.chalmers.group21.spa.model.entities.Board;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryBoard implements BoardRepository {
    private Map<UUID, Board> boards  = new HashMap<>();

    @Override
    public void save(Board board) {
        boards.put(board.getId(), board);
    }

    @Override
    public void delete(UUID id) {
        boards.remove(id);
    }

    @Override
    public Board findById(UUID id) {
        return boards.get(id);
    }

    @Override
    public Board findByGroup(UUID groupId) {
        for(Board board: boards.values()){
            if(board.getGroupId() == groupId){
                return board;
            }
        }
        return null;
    }
}

package Model.entities;

import java.util.UUID;

public class TaskAssignment {
    private final UUID userId;

    public TaskAssignment(UUID userId){
        this.userId = userId;
    }

    public UUID getUserId(){
        return userId;
    }
}

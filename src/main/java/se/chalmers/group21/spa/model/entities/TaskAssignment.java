package se.chalmers.group21.spa.model.entities;

import java.util.Objects;
import java.util.UUID;

public class TaskAssignment {
    private final UUID userId;

    public TaskAssignment(UUID userId){
        this.userId = userId;
    }

    public UUID getUserId(){
        return userId;
    }

    @Override
    public boolean equals(Object obj) { //AI
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TaskAssignment)) {
            return false;
        }

        TaskAssignment other = (TaskAssignment) obj;
        return Objects.equals(userId, other.userId);
    }

    @Override
    public int hashCode() { //AI
        return Objects.hash(userId);
    }
}


package se.chalmers.group21.spa.model.entities;

import java.util.Objects;
import java.util.UUID;

/*
Created by: Thyra Öfverström
    This class represents the assignments of a group task to a specific user. It does this by storing the unique ID
    of the assigned user. It also allows multiple users to be assignedto the same group task. It also prevents the same
    user from being assigned multiple times to the same task.

    Methods genereated by AI: equals(Object obj)
 */
public class TaskAssignment {
    private final UUID userId;

    public TaskAssignment(UUID userId){
        this.userId = userId;
    }

    public UUID getUserId(){
        return userId;
    }

    //AI
    @Override
    public boolean equals(Object obj) {
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


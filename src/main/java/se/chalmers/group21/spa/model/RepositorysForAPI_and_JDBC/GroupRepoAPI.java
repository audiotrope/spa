package se.chalmers.group21.spa.model.RepositorysForAPI_and_JDBC;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.chalmers.group21.spa.model.Database.SupabaseClient;
import se.chalmers.group21.spa.model.Group.Group;
import se.chalmers.group21.spa.model.Group.Roles;
import se.chalmers.group21.spa.model.Interfaces.GroupRepository;
import se.chalmers.group21.spa.model.User.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

//AI generated, I created the groupRepo for JDBC, but we decided to use API and I didn't know how to translate it.

public class GroupRepoAPI implements GroupRepository {
    private final SupabaseClient supabase;
    private final Gson gson = new Gson();

    public GroupRepoAPI(SupabaseClient supabase) {
        this.supabase = supabase;
    }

    //Sparar gruppen till supabase
    @Override
    public void save(Group group) {
        String json = gson.toJson(Map.of(
                "id", group.getGroupCode().toString(),
                "name", group.groupName
                ));
        try {
            supabase.post("groups", json);
            for (Map.Entry<User, Roles> entry : group.members.entrySet()) {
                addMember(group.getGroupCode(), entry.getKey(), entry.getValue());
            }

        } catch (IOException | InterruptedException  | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addMember(UUID groupId, User user) throws SQLException {
        addMember(groupId, user, Roles.MEMBER);
    }


    //Sparar gruppmedlemmar till supbase, group_members
    public void addMember(UUID groupId, User user, Roles role) throws SQLException {
        String json = gson.toJson(Map.of(
                "group_id", groupId.toString(),
                "user_id", user.getUserId().toString(),
                "username", user.username,
                "role", role.name().toLowerCase()));
        try {
            supabase.post("group_members", json);
        } catch (IOException | InterruptedException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void removeMember(UUID groupId, User user) throws SQLException {
        try {
            supabase.delete("group_members?group_id=eq." + groupId
                    + "&user_id=eq." + user.getUserId());
        } catch (IOException | InterruptedException e) {
            throw new SQLException(e);
        }
    }

    @Override
    //Skapar ny grupp i minnet med information från databasen
    public Group findGroupById(UUID id) {
        try {
            String body = supabase.get("groups?id=eq." + id + "&select=id,name");
            JsonArray rows = JsonParser.parseString(body).getAsJsonArray();
            if (rows.isEmpty()) {
                return null;
            }
            JsonObject row = rows.get(0).getAsJsonObject();
            return new Group(row.get("name").getAsString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeRole(UUID groupId, User userThatChanges, User userToChange, Roles role)
            throws SQLException {
        String json = gson.toJson(Map.of("role", role.name()));
        try {
            supabase.patch("group_members?group_id=eq." + groupId
                    + "&user_id=eq." + userToChange.getUserId(), json);
        } catch (IOException | InterruptedException e) {
            throw new SQLException(e);
        }
    }
}
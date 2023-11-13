package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.rococo.data.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class UserJson {
    @JsonProperty("id")
    UUID id;
    @JsonProperty("username")
    String username;
    @JsonProperty("firstname")
    String firstname;
    @JsonProperty("lastname")
    String lastname;
    @JsonProperty("avatar")
    String avatar;

    public UserJson() {
    }
    public static UserJson fromEntity(UserEntity entity) {
        UserJson usr = new UserJson();
        usr.setId(entity.getId());
        usr.setUsername(entity.getUsername());
        usr.setFirstname(entity.getFirstname());
        usr.setLastname(entity.getLastname());
        byte[] photo = entity.getAvatar();
        usr.setAvatar(photo != null && photo.length > 0 ? new String(entity.getAvatar(), StandardCharsets.UTF_8) : null);
        return usr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson userJson = (UserJson) o;
        return Objects.equals(id, userJson.id) && Objects.equals(username, userJson.username) && Objects.equals(firstname, userJson.firstname) && Objects.equals(lastname, userJson.lastname) && Objects.equals(avatar, userJson.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, lastname, avatar);
    }
}

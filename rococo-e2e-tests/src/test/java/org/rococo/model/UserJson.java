package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.rococo.db.model.user.Authority;
import org.rococo.db.model.user.AuthorityEntity;
import org.rococo.db.model.user.UserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
public class UserJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("photo")
    private String photo;
    transient String password;

    public UserJson() {
    }

    public static UserJson fromEntity(UserEntity entity) {
        UserJson usr = new UserJson();
        usr.setId(entity.getId());
        usr.setUsername(entity.getUsername());
        return usr;
    }

    public static UserEntity toEntity(UserJson userJson) {
        UserEntity authUser = new UserEntity();
        authUser.setId(userJson.getId());
        authUser.setUsername(userJson.getUsername());
        authUser.setPassword(userJson.getPassword());
        authUser.setEnabled(true);
        authUser.setAccountNonExpired(true);
        authUser.setAccountNonLocked(true);
        authUser.setCredentialsNonExpired(true);
        authUser.setAuthorities(new ArrayList<>(Arrays.stream(Authority.values())
                .map(a -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(a);
                    ae.setUser(authUser);
                    return ae;
                }).toList()));
        return authUser;
    }

    public static UserJson emptyUserJson() {
        UserJson userJson = new UserJson();
        userJson.setUsername("");
        userJson.setPassword("");
        return userJson;
    }
}

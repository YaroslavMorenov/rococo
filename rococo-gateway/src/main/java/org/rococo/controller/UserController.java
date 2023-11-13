package org.rococo.controller;

import org.rococo.model.UserJson;
import org.rococo.service.UserDataClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDataClient userDataClient;

    @Autowired
    public UserController(UserDataClient userDataClient) {
        this.userDataClient = userDataClient;
    }

    @GetMapping()
    public UserJson currentUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userDataClient.currentUser(username);
    }

    @PatchMapping()
    public UserJson updateUserInfo(@AuthenticationPrincipal Jwt principal,
                                   @RequestBody UserJson user) {
        String username = principal.getClaim("sub");
        if (!Objects.equals(username, user.username())) {
            throw new ResponseStatusException(FORBIDDEN, "Can`t access to another user");
        }
        return userDataClient.updateUserInfo(user);
    }
}

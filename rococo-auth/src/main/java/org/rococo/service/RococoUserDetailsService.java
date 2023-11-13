package org.rococo.service;

import org.rococo.data.UserEntity;
import org.rococo.data.repository.UserRepository;
import org.rococo.domain.RococoUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RococoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public RococoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new RococoUserPrincipal(user);
    }
}

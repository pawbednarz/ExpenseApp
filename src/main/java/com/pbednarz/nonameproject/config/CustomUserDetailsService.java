package com.pbednarz.nonameproject.config;

import com.pbednarz.nonameproject.user.model.User;
import com.pbednarz.nonameproject.user.model.UserRole;
import com.pbednarz.nonameproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                convertAuthorities(user.getUserRoles())
        );
    }

    private Set<GrantedAuthority> convertAuthorities (Set<UserRole> userRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new SimpleGrantedAuthority(ur.getRole())));
        return authorities;
    }
}

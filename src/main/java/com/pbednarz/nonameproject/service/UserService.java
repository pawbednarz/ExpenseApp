package com.pbednarz.nonameproject.service;

import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.model.user.User;
import com.pbednarz.nonameproject.model.user.UserRole;
import com.pbednarz.nonameproject.repository.UserRepository;
import com.pbednarz.nonameproject.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addWithDefaultRole(User user) {
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        user.getUserRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    public void addExpenseToUser(String username, Expense expense) {
        User user = userRepository.findByUsername(username);
        expense.setUser(user);
        user.getExpenses().add(expense);
        userRepository.save(user);
    }
}

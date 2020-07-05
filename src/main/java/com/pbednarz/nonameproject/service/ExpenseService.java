package com.pbednarz.nonameproject.service;

import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.repository.ExpenseRepository;
import com.pbednarz.nonameproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.util.Date;

@Service
public class ExpenseService {

    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public void addNewExpenseToUser(Expense expense, String username) {
        if (expense.getDate() == null) {
            expense.setDate(Date.from(Instant.now()));
        }
        expense.setUser(userRepository.findByUsername(username));
        expenseRepository.save(expense);
    }

    public void checkForErrors(Expense expenses, BindingResult bindingResult) {

    }
}

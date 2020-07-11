package com.pbednarz.nonameproject.expense.service;

import com.pbednarz.nonameproject.expense.model.Expense;
import com.pbednarz.nonameproject.expense.repository.ExpenseRepository;
import com.pbednarz.nonameproject.user.repository.UserRepository;
import com.pbednarz.nonameproject.user.service.ErrorChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExpenseService implements ErrorChecker {

    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public void addNewExpenseToUser(Expense expense, String username) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDateTime.now());
        }
        expense.setUser(userRepository.findByUsername(username));
        expenseRepository.save(expense);
    }

    public Expense modifyExpense(Expense toEdit, Expense data) {
        toEdit.setCategory(data.getCategory());
        toEdit.setValue(data.getValue());
        toEdit.setModificationDate(LocalDateTime.now());
        return toEdit;
    }

    public boolean checkPermissions(long expenseId, String username) {
        return expenseRepository.findByIdAndUsername(expenseId, username) != null;
    }
}

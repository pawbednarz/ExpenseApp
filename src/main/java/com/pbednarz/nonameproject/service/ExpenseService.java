package com.pbednarz.nonameproject.service;

import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.repository.ExpenseRepository;
import com.pbednarz.nonameproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    public String checkForErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder response = new StringBuilder("{\"error\":\"");
            errors.forEach(err -> {
                response.append(err.getField());
                response.append(" ");
                response.append(err.getDefaultMessage());
            });
            response.append("\"}");
            return response.toString();
        } else {
            return "";
        }
    }
}

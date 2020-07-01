package com.pbednarz.nonameproject.controller;

import com.pbednarz.nonameproject.model.Category;
import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.model.user.User;
import com.pbednarz.nonameproject.repository.ExpenseRepository;
import com.pbednarz.nonameproject.repository.UserRepository;
import com.pbednarz.nonameproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    UserService userService;
    UserRepository userRepository;
    ExpenseRepository expenseRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return  "registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.addWithDefaultRole(user);
        }
        return "home";
    }

    @GetMapping("/expenses")
    public String expenses(Model model,  Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        // TODO maybe find by username (custom query)
        List<Expense> expenses = expenseRepository.findAllByUserId(user.getId());
        model.addAttribute("expenses", expenses);
        return "expense";
    }

    @GetMapping("/addExpense")
    public String expenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "addExpense";
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestParam double value,
                             @RequestParam String category,
                             Model model,
                             Authentication auth) {
        Expense expense = new Expense(new BigDecimal(value), Category.CAR, Date.from(Instant.now()));
        userService.addExpenseToUser(auth.getName(), expense);
        model.addAttribute("expenses", expenseRepository.findAllByUsername(auth.getName()));
        return "redirect:/expenses";
    }
}

package com.pbednarz.nonameproject;

import com.pbednarz.nonameproject.expense.model.Category;
import com.pbednarz.nonameproject.expense.model.Expense;
import com.pbednarz.nonameproject.expense.repository.CategoryRepository;
import com.pbednarz.nonameproject.expense.service.ExpenseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class NonameprojectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NonameprojectApplication.class, args);

        CategoryRepository categoryRepository = ctx.getBean(CategoryRepository.class);

        Expense e1 = new Expense(new BigDecimal(12.34), categoryRepository.findByName("Clothes"));
        Expense e2 = new Expense(new BigDecimal(333.33), categoryRepository.findByName("Electronics"));
        Expense e3 = new Expense(new BigDecimal(65.54), categoryRepository.findByName("Transport"));

        ExpenseService expenseService = ctx.getBean(ExpenseService.class);
        expenseService.addNewExpenseToUser(e1, "pablo");
        expenseService.addNewExpenseToUser(e2, "pablo");
        expenseService.addNewExpenseToUser(e3, "pablo");
    }

}

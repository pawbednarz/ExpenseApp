package com.pbednarz.nonameproject;

import com.pbednarz.nonameproject.model.Category;
import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.repository.ExpenseRepository;
import com.pbednarz.nonameproject.service.ExpenseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class NonameprojectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NonameprojectApplication.class, args);

        Expense e1 = new Expense(new BigDecimal(12.34), Category.CLOTHES);
        Expense e2 = new Expense(new BigDecimal(333.33), Category.ELECTRONICS);
        Expense e3 = new Expense(new BigDecimal(65.54), Category.CAR);

        ExpenseService expenseService = ctx.getBean(ExpenseService.class);
        expenseService.addNewExpenseToUser(e1, "pablo");
        expenseService.addNewExpenseToUser(e2, "pablo");
        expenseService.addNewExpenseToUser(e3, "pablo");
    }

}

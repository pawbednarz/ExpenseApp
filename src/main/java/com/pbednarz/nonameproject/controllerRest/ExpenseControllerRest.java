package com.pbednarz.nonameproject.controllerRest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbednarz.nonameproject.model.Category;
import com.pbednarz.nonameproject.model.Expense;
import com.pbednarz.nonameproject.repository.ExpenseRepository;
import com.pbednarz.nonameproject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${com.pbednarz.apiPath}/expense")
public class ExpenseControllerRest {

    // TODO ADD AUTHENTICATION!!!

    private ExpenseRepository expenseRepository;
    private ExpenseService expenseService;

    @Autowired
    public ExpenseControllerRest(ExpenseRepository expenseRepository,
                                 ExpenseService expenseService) {
        this.expenseRepository = expenseRepository;
        this.expenseService = expenseService;
    }

    // TODO fix app behavior on ${com.pbednarz.apiPath}/expense url (without last "/")

    @GetMapping
    public ResponseEntity<Expense> getExpenses(Authentication auth) {
        List<Expense> expenses = expenseRepository.findAllByUsername(auth.getName());
        return new ResponseEntity(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable int id) {
        Expense expense = expenseRepository.findById((long) id).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (expense == null) {
            return new ResponseEntity("{\"error\":\"expense with provided id not found\"}", headers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(expense, headers, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity addExpense(@RequestBody @Valid Expense expense,
                                              BindingResult bindingResult) throws URISyntaxException {
        String error = expenseService.checkForErrors(bindingResult);
        if (!error.isEmpty()){
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
            expenseService.addNewExpenseToUser(expense, "pablo");
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateExpense(@PathVariable int id, @RequestBody @Valid Expense expense, BindingResult bindingResult) {
        String error = expenseService.checkForErrors(bindingResult);
        System.out.println(error);
        if (!error.isEmpty()){
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
        Expense toEdit = expenseRepository.findById((long)id).get();
        toEdit = expenseService.modifyExpense(toEdit, expense);
        expenseRepository.save(toEdit);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity deleteExpenseById(@RequestBody Map<String, Object> jsonWithId) {
        Object idObject = jsonWithId.getOrDefault("id", null);
        Long id;
        try {
            id = Long.parseLong(idObject.toString());
        } catch (NumberFormatException e) {
            return new ResponseEntity("\"error\":\"provided data is not a integer\"", HttpStatus.BAD_REQUEST);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity("\"error\":\"wrong json format\"", HttpStatus.BAD_REQUEST);
        }
        expenseRepository.deleteById(id);
        return new ResponseEntity(id, HttpStatus.NO_CONTENT);
    }
}

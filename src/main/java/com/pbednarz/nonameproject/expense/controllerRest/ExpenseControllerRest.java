package com.pbednarz.nonameproject.expense.controllerRest;

import com.pbednarz.nonameproject.expense.model.Expense;
import com.pbednarz.nonameproject.expense.repository.ExpenseRepository;
import com.pbednarz.nonameproject.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${com.pbednarz.apiPath}/expense")
public class ExpenseControllerRest {

    // TODO do tests for PUT mapping

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
    public ResponseEntity<Expense> getExpense(@PathVariable int id, Authentication auth) {
        // TODO only if user have permissions for that
        if (expenseService.checkPermissions(id, auth.getName())) {
            Expense expense = expenseRepository.findById((long) id).orElse(null);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            if (expense == null) {
                return new ResponseEntity("{\"error\":\"expense with provided id not found\"}", headers, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(expense, headers, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public ResponseEntity addExpense(@RequestBody @Valid Expense expense,
                                     BindingResult bindingResult,
                                     Authentication auth) {
        String error = expenseService.checkForErrors(bindingResult);
        if (!error.isEmpty()){
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
            expenseService.addNewExpenseToUser(expense, auth.getName());
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateExpense(@PathVariable int id,
                                        @RequestBody @Valid Expense expense,
                                        BindingResult bindingResult,
                                        Authentication auth) {
        if(expenseService.checkPermissions(id, auth.getName())) {
            String error = expenseService.checkForErrors(bindingResult);
            if (!error.isEmpty()){
                return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
            }
            Expense toEdit = expenseRepository.findById((long)id).get();
            toEdit = expenseService.modifyExpense(toEdit, expense);
            expenseRepository.save(toEdit);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity deleteExpenseById(@RequestBody Map<String, Object> jsonWithId, Authentication auth) {
        Object idObject = jsonWithId.getOrDefault("id", null);
        Long id;
        try {
            id = Long.parseLong(idObject.toString());
        } catch (NumberFormatException e) {
            return new ResponseEntity("\"error\":\"provided data is not a integer\"", HttpStatus.BAD_REQUEST);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity("\"error\":\"wrong json format\"", HttpStatus.BAD_REQUEST);
        }
        if (expenseService.checkPermissions(id, auth.getName())) {
            expenseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // TODO think about giving response code unauthorized on unauthorized request
            return ResponseEntity.badRequest().build();
        }
    }
}

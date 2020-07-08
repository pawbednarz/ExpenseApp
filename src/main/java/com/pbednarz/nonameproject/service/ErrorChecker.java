package com.pbednarz.nonameproject.service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public interface ErrorChecker {

    default String checkForErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder response = new StringBuilder("{\"error\":\"");
            errors.forEach(err -> {
                response.append(err.getField());
                response.append(" ");
                response.append(err.getDefaultMessage());
                response.append(", ");
            });
            response.delete(response.length() - 2, response.length());
            response.append("\"}");
            return response.toString();
        } else {
            return "";
        }
    }
}

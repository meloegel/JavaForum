package com.javaforum.JavaForum.services;


import com.javaforum.JavaForum.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;


@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions {

    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause) {
        while ((cause != null) && !(cause instanceof org.hibernate.exception.ConstraintViolationException || cause instanceof MethodArgumentNotValidException)) {
            System.out.println(cause.getClass().toString());
            cause = cause.getCause();
        }
        List<ValidationError> errorList = new ArrayList<>();

        if (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException){
                org.hibernate.exception.ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError newError = new ValidationError();
                newError.setCode(ex.getMessage());
                newError.setMessage(ex.getConstraintName());
                errorList.add(newError);
            } else {
                if (cause instanceof MethodArgumentNotValidException) {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
                    for (FieldError err : fieldErrors){
                        ValidationError newError = new ValidationError();
                        newError.setCode(err.getField());
                        newError.setMessage(err.getDefaultMessage());
                        errorList.add(newError);
                    }
                } else {
                    System.out.println("Error in producing constraint violations exceptions");
                }
            }
        }
        return errorList;
    }

    @Override
    public boolean isAuthorizedToMakeChange(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName().toLowerCase()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return true;
        } else {
            throw new OAuth2AccessDeniedException();
        }
    }
}

package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.models.ValidationError;
import java.util.List;

public interface HelperFunctions {
    List<ValidationError> getConstraintViolation(Throwable cause);

    boolean isAuthorizedToMakeChange(String username);
}

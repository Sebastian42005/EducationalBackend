package com.example.educationalbackend.exception;

import com.example.educationalbackend.exception.exceptions.EntityAlreadyExistsException;
import com.example.educationalbackend.exception.exceptions.EntityNotFoundException;
import com.example.educationalbackend.exception.exceptions.RoleNotExistsException;
import com.example.educationalbackend.exception.exceptions.UserNotLoggedInException;
import com.example.educationalbackend.exception.exceptions.WrongLoginCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionsRecord> handleEntityNotfound(EntityNotFoundException ex, ServletWebRequest request) {
        String notFound = " not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionsRecord(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage() + notFound,
                ex.getId() != 0 ? ex.getMessage() + " with id " + ex.getId() + notFound : ex.getMessage() + notFound,
                request.getRequest().getRequestURI()
        ));
    }

    @ExceptionHandler(WrongLoginCredentialsException.class)
    protected ResponseEntity<ExceptionsRecord> handleWrongLoginCredentialsException(WrongLoginCredentialsException ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionsRecord(
                HttpStatus.UNAUTHORIZED.value(),
                "Wrong login credentials",
                "Could not authenticate user with given credentials",
                request.getRequest().getRequestURI()
        ));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<ExceptionsRecord> handleEntityAlreadyExists(EntityAlreadyExistsException ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionsRecord(
                HttpStatus.CONFLICT.value(),
                ex.getEntityType().getName() + " already exists",
                ex.getEntityType().getName() + " with id " + ex.getId() + " already exists",
                request.getRequest().getRequestURI()
        ));
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    protected ResponseEntity<ExceptionsRecord> handleUserNotLoggedIn(UserNotLoggedInException ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionsRecord(
                HttpStatus.UNAUTHORIZED.value(),
                "User not logged in",
                "User is not logged in",
                request.getRequest().getRequestURI()
        ));
    }

    @ExceptionHandler(RoleNotExistsException.class)
    protected ResponseEntity<ExceptionsRecord> handleRoleNotExists(RoleNotExistsException ex, ServletWebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionsRecord(
                HttpStatus.NOT_FOUND.value(),
                "Role doesn't exist",
                "Role '" + ex.getRole() + "' doesn't exist",
                request.getRequest().getRequestURI()
        ));
    }
}

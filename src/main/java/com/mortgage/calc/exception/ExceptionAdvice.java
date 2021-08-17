package com.mortgage.calc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR = "System error occurred. Contact the system administrator";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder message = new StringBuilder("Invalid input - ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            message.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return getErrorEntity(message.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ValidationException.class
    })
    protected ResponseEntity<Object> handleConflict(ValidationException ex) {
        StringBuilder message = new StringBuilder("Invalid input - ");
        message.append(ex.getMessage());
        return getErrorEntity(message.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            Exception.class,
    })
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception e) {
        return getErrorEntity(GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getErrorEntity(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        return new ResponseEntity(response, status);
    }


}

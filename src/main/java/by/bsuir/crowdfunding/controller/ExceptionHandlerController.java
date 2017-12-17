package by.bsuir.crowdfunding.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.bsuir.crowdfunding.exception.NotEnoughMoneyException;
import by.bsuir.crowdfunding.exception.ValueNotFoundException;
import by.bsuir.crowdfunding.exception.WrongUserCredentialsException;
import lombok.extern.slf4j.Slf4j;
import by.bsuir.crowdfunding.rest.Error;


@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        log.error("Request processing error", e);
        return new ResponseEntity<>(createStandardResponse(e, INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<Error> createStandardResponse(Exception e, String message) {
        return Collections.singletonList(Error.builder()
                .code(message)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Error> errors = new ArrayList<>();
        if (e.getBindingResult() != null && e.getBindingResult().getAllErrors() != null) {
            for (final ObjectError objectError : e.getBindingResult().getAllErrors()) {
                errors.add(Error.builder()
                        .message(objectError.getDefaultMessage())
                        .code(objectError.getCode())
                        .build());
            }
        } else {
            errors.add(Error.builder()
                    .code(INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity handleBindException(BindException e) {
        List<Error> errors = new ArrayList<>();
        if (e.getBindingResult() != null && e.getBindingResult().getAllErrors() != null) {
            for (final ObjectError objectError : e.getBindingResult().getAllErrors()) {
                errors.add(Error.builder()
                        .message(objectError.getDefaultMessage())
                        .code(objectError.getCode())
                        .build());
            }

        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotEnoughMoneyException.class})
    public ResponseEntity handleNotEnoughMoneyExpection(NotEnoughMoneyException e) {
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValueNotFoundException.class})
    public ResponseEntity handleValueNotFoundException(ValueNotFoundException e) {
        return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WrongUserCredentialsException.class})
    public ResponseEntity handleWrongUserCredentialsException(WrongUserCredentialsException e) {
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }
}

package by.bsuir.crowdfunding.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import by.bsuir.crowdfunding.rest.Error;

@AllArgsConstructor
@Getter
public class NotEnoughMoneyException extends Exception {
    private List<Error> errors;
}
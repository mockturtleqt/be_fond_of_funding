package by.bsuir.crowdfunding.exception;

import by.bsuir.crowdfunding.rest.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValueNotFoundException extends Exception {
    private List<Error> errors;
}

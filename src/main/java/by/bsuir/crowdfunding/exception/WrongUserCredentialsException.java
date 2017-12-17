package by.bsuir.crowdfunding.exception;

import java.util.List;
import by.bsuir.crowdfunding.rest.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WrongUserCredentialsException extends Exception  {
    private List<Error> errors;
}

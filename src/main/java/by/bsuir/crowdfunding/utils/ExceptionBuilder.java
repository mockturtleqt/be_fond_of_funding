package by.bsuir.crowdfunding.utils;

import by.bsuir.crowdfunding.rest.Error;

import java.util.Collections;
import java.util.List;

public class ExceptionBuilder {

    public static List<Error> buildWrongUserLoginException() {
        return Collections.singletonList(Error.builder()
                .code("400")
                .message("Login is incorrect")
                .build());
    }

    public static List<Error> buildWrongUserPasswordException() {
        return Collections.singletonList(Error.builder()
                .code("400")
                .message("Password is incorrect")
                .build());
    }

    public static Error buildNoSuchUserException(Long id) {
        return Error.builder()
                .code("400")
                .message(String.format("There's no user with the specified id (%s)", id))
                .build();
    }


    public static List<Error> buildExpiredTokenException() {
        Error error = Error.builder()
                .code("400")
                .message("Token has expired")
                .description("Please register once again and confirm registration via email.")
                .build();
        return Collections.singletonList(error);
    }

    public static List<Error> buildInvalidTokenException() {
        Error error = Error.builder()
                .code("400")
                .message("This token doesn't exist.")
                .description("Please use a valid token.")
                .build();
        return Collections.singletonList(error);
    }


    public static Error buildNotEnoughMoneyError() {
        return Error.builder()
                .code("400")
                .message("Not enough money")
                .description("There's not enough money on your balance. You can put money on your balance using a credit card")
                .build();
    }

    public static Error buildNoSuchProjectException(Long id) {
        return Error.builder()
                .code("400")
                .message(String.format("There's no project with the specified id (%s)", id))
                .build();
    }

}

package by.bsuir.crowdfunding.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduledRequestNotFoundException extends Exception {
    private List<Error> errors;
}

package by.bsuir.crowdfunding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.bsuir.crowdfunding.jobs.ProjectExpirationJob;
import by.bsuir.crowdfunding.jobs.TokenInvalidationJob;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/jobs")
public class ScheduleController {

    private final ProjectExpirationJob projectExpirationJob;
    private final TokenInvalidationJob tokenInvalidationJob;

    @Autowired
    public ScheduleController(ProjectExpirationJob projectExpirationJob, TokenInvalidationJob tokenInvalidationJob) {
        this.projectExpirationJob = projectExpirationJob;
        this.tokenInvalidationJob = tokenInvalidationJob;
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to invalidate projects which due date has come. " +
            "If the necessary amount of money was raised, the project creator gets the money," +
            "if it wasn't, the funders get their money back.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job to expire projects was started", response = String.class)
    })
    @RequestMapping(value = "/expireProjects", method = RequestMethod.GET)
    public String expireProjects() {
        projectExpirationJob.run();
        return "Job to expire projects was executed.";
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to invalidate expired registration tokens.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job to invalidate registration tokens was started", response = String.class)
    })
    @RequestMapping(value = "/invalidateRegistrationTokens", method = RequestMethod.GET)
    public String invalidateRegistrationTokens() {
        tokenInvalidationJob.run();
        return "Job to invalidate registration tokens was executed.";
    }


}

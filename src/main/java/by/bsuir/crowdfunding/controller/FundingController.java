package by.bsuir.crowdfunding.controller;

import by.bsuir.crowdfunding.exception.NotEnoughMoneyException;
import by.bsuir.crowdfunding.exception.ValueNotFoundException;
import by.bsuir.crowdfunding.rest.FundingDto;
import by.bsuir.crowdfunding.service.FundingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@CrossOrigin
@RestController
public class FundingController {

    private final FundingService fundingService;

    @Autowired
    public FundingController(FundingService fundingService) {
        this.fundingService = fundingService;
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to fund a project.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Money transfer was made", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Not enough money.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/user/fundingInfo/fundProject")
    public void fundProject(@RequestBody @Valid @ApiParam(value = "request") FundingDto fundingDto) throws NotEnoughMoneyException, ValueNotFoundException {
        fundingService.fundProject(fundingDto);
    }
}

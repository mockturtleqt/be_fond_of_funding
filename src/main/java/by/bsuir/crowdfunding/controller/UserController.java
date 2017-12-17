package by.bsuir.crowdfunding.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import by.bsuir.crowdfunding.exception.WrongUserCredentialsException;
import by.bsuir.crowdfunding.rest.CompleteUserDto;
import by.bsuir.crowdfunding.rest.UserDto;
import by.bsuir.crowdfunding.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import static by.bsuir.crowdfunding.utils.JwtUtils.TOKEN_PREFIX;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to register user in the system.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User was registered.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/registerUser")
    public void registerUser(@RequestBody @Valid @ApiParam(value = "request") CompleteUserDto userDto) {
        userService.registerUser(userDto);
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to sign in user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User has logged in", response = String.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/login")
    public ResponseEntity login(@RequestBody @Valid @ApiParam(value = "request") UserDto userDto) throws UnsupportedEncodingException, WrongUserCredentialsException {
        HttpHeaders responseHeaders = new HttpHeaders();
        String jwt = userService.loginUser(userDto);
        responseHeaders.set(AUTHORIZATION, TOKEN_PREFIX + jwt);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @ResponseStatus(OK)
    @ApiOperation("Get user info by name.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User info", response = CompleteUserDto.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/findUserByLogin")
    public CompleteUserDto getCurrentCalValByLdz(@RequestParam(name = "login") String login) {
        return userService.findUserByLogin(login);
    }

}

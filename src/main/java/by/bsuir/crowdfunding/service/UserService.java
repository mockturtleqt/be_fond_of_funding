package by.bsuir.crowdfunding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import by.bsuir.crowdfunding.exception.WrongUserCredentialsException;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.CompleteUserDto;
import by.bsuir.crowdfunding.rest.Error;
import by.bsuir.crowdfunding.rest.UserDto;
import by.bsuir.crowdfunding.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

import static by.bsuir.crowdfunding.model.enumeration.UserRole.USER;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtils jwtUtils, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    public User registerUser(CompleteUserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(encoder.encode(userDto.getPassword()))
                .balance(BigDecimal.ZERO)
                .role(USER)
                .build();
        return userRepository.save(user);
    }

    public CompleteUserDto findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        if (nonNull(user)) {
            return CompleteUserDto.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .userRole(user.getRole())
                    .build();
        } else {
            return null;
        }
    }

    public String loginUser(UserDto userDto) throws UnsupportedEncodingException, WrongUserCredentialsException {
        User user = userRepository.findUserByLogin(userDto.getLogin());
        if (nonNull(user)) {
            if (BCrypt.checkpw(userDto.getPassword(), user.getPassword())) {
                return jwtUtils.generateJwt(user);
            }
            throw new WrongUserCredentialsException(createWrongUserPasswordException());
        }
        throw new WrongUserCredentialsException(createWrongUserLoginException());
    }

    private List<Error> createWrongUserLoginException() {
        return Collections.singletonList(Error.builder()
        .code("400")
        .message("Login is incorrect")
        .build());
    }

    private List<Error> createWrongUserPasswordException() {
        return Collections.singletonList(Error.builder()
        .code("400")
        .message("Password is incorrect")
        .build());
    }
}

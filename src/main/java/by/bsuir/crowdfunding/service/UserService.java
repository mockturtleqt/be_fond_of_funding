package by.bsuir.crowdfunding.service;

import by.bsuir.crowdfunding.exception.AuthorizationTokenException;
import by.bsuir.crowdfunding.exception.ValueNotFoundException;
import by.bsuir.crowdfunding.exception.WrongUserCredentialsException;
import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.Error;
import by.bsuir.crowdfunding.rest.*;
import by.bsuir.crowdfunding.utils.ConverterUtils;
import by.bsuir.crowdfunding.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static by.bsuir.crowdfunding.model.enumeration.UserRole.USER;
import static by.bsuir.crowdfunding.utils.ExceptionBuilder.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder;
    private final VerificationTokenService verificationTokenService;
    private final FundingService fundingService;

    @Autowired
    public UserService(UserRepository userRepository,
                       JwtUtils jwtUtils, BCryptPasswordEncoder encoder,
                       VerificationTokenService verificationTokenService,
                       FundingService fundingService) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.verificationTokenService = verificationTokenService;
        this.fundingService = fundingService;
    }

    public User registerUser(RegisterUserDto userDto) {
        User user = userRepository.findUserByLogin(userDto.getLogin());
        List<Error> errors = new ArrayList<>();
        if (isNull(user)) {
            user = User.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .login(userDto.getLogin())
                    .password(encoder.encode(userDto.getPassword()))
                    .balance(BigDecimal.ZERO)
                    .role(USER)
                    .enabled(false)
                    .build();
            user = userRepository.save(user);
            verificationTokenService.sendEmailToConfirmRegistration(user);
        } else if (!user.isEnabled()) {
            verificationTokenService.sendEmailToConfirmRegistration(user);
        } else if (nonNull(userRepository.findUserByEmail(userDto.getEmail()))) {
            Error userWithEmailExists = Error.builder()
                    .code("400")
                    .message("User with this email already exists")
                    .description("Please use another email")
                    .build();
            errors.add(userWithEmailExists);
        } else {
            Error userWithLoginExists = Error.builder()
                    .code("400")
                    .message("User with this login already exists")
                    .description("Please use another login")
                    .build();
            errors.add(userWithLoginExists);
        }
        return user;
    }

    public User updateUser(UpdateUserDto userDto) {
        User user = userRepository.findOne(userDto.getUserId());
        if (nonNull(user)) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirthDate(ConverterUtils.convertLocalDateToTimestamp(userDto.getBirthDate()));
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setProfilePicture(userDto.getProfilePicture());
            user = userRepository.save(user);
            return user;
        } else return null;
    }

    public User addMoneyToUserBalance(UserBalanceDto userBalanceDto) throws ValueNotFoundException {
        User user = userRepository.findOne(userBalanceDto.getUserId());
        if (nonNull(user)) {
            BigDecimal currentBalance = user.getBalance();
            if (nonNull(currentBalance)) {
                user.setBalance(currentBalance.add(userBalanceDto.getMoneyToAdd()));
            } else {
                user.setBalance(userBalanceDto.getMoneyToAdd());
            }
            return userRepository.save(user);
        }
        throw new ValueNotFoundException(Collections.singletonList(buildNoSuchUserException(userBalanceDto.getUserId())));
    }

    public User findEnabledUserByLogin(String login) {
        return userRepository.findUserByLoginAndEnabled(login, true);
//        User user = userRepository.findUserByLoginAndEnabled(login, true);
//        if (nonNull(user)) {
//            return CompleteUserDto.builder()
//                    .firstName(user.getFirstName())
//                    .lastName(user.getLastName())
//                    .email(user.getEmail())
//                    .login(user.getLogin())
//                    .password(user.getPassword())
//                    .userRole(user.getRole())
//                    .userId(user.getId())
//                    .build();
//        } else {
//            return null;
//        }
    }

    public Set<User> findFundersByProejctId(Long projectId) {
        return fundingService.findFundingInfoByProject(projectId)
                .stream()
                .map(FundingInfo::getUser)
                .collect(Collectors.toSet());
    }

    public String loginUser(UserLoginDto userDto) throws UnsupportedEncodingException, WrongUserCredentialsException {
        User user = userRepository.findUserByLoginAndEnabled(userDto.getLogin(), true);
        if (nonNull(user)) {
            if (BCrypt.checkpw(userDto.getPassword(), user.getPassword())) {
                return jwtUtils.generateJwt(user);
            }
            throw new WrongUserCredentialsException(buildWrongUserPasswordException());
        }
        throw new WrongUserCredentialsException(buildWrongUserLoginException());
    }

    public void confirmRegistration(String token) throws AuthorizationTokenException {
        User user = verificationTokenService.getUserFromToken(token);
        user.setEnabled(true);
        userRepository.save(user);
    }
}

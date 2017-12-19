package by.bsuir.crowdfunding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import by.bsuir.crowdfunding.exception.AuthorizationTokenException;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.model.VerificationToken;
import by.bsuir.crowdfunding.repository.VerificationTokenRepository;
import by.bsuir.crowdfunding.rest.Error;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class VerificationTokenService {

    private final MessageSource messages;
    private final JavaMailSender mailSender;
    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${application.url}")
    private String applicationUrl;

    private final static String CONFIRMATION_URL = "/confirmRegistration?token=%s";
    private final static String MESSAGE_SUBJECT = "Be fond of funding -- registration confirmation";
    private final static String MESSAGE_BODY = "Hello, %s %s!\nPlease follow this link to confirm your registration: \n\n";

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository,
                                    MessageSource messages,
                                    JavaMailSender mailSender) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    public void sendEmailToConfirmRegistration(User user) {
        String confirmationUrl = applicationUrl + String.format(CONFIRMATION_URL, createToken(user));
        String messageBody = String.format(MESSAGE_BODY, user.getFirstName(), user.getLastName());
        mailSender.send(createConfirmationEmail(user.getEmail(), confirmationUrl, messageBody));
    }

    public void invalidateTokens() {
        List<VerificationToken> expiredRokens = verificationTokenRepository
                .findByExpirationDateBeforeAndIsActive(Timestamp.valueOf(LocalDateTime.now()), true);
        expiredRokens.forEach(token -> token.setIsActive(false));
        verificationTokenRepository.save(expiredRokens);
    }

    public User getUserFromToken(String tokenFromUser) throws AuthorizationTokenException {
        if (isNotBlank(tokenFromUser)) {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(tokenFromUser);
            if (nonNull(verificationToken)) {
                if (verificationToken.getIsActive()) {
                    return verificationToken.getUser();
                } else {
                    throw new AuthorizationTokenException(buildExpiredTokenException());
                }
            }
        }
        throw new AuthorizationTokenException(buildInvalidTokenException());
    }

    public VerificationToken findByUserId(long userId) {
        return verificationTokenRepository.findByUserId(userId);
    }

    private SimpleMailMessage createConfirmationEmail(String userEmail, String confirmationUrl, String messageBody) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEmail);
        email.setSubject(MESSAGE_SUBJECT);
        email.setText(messageBody + confirmationUrl);
        return email;
    }

    private String createToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now().plusDays(1)))
                .user(user)
                .isActive(true)
                .build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private List<Error> buildExpiredTokenException() {
        Error error = Error.builder()
                .code("400")
                .message("Token has expired")
                .description("Please register once again and confirm registration via email.")
                .build();
        return Collections.singletonList(error);
    }

    private List<Error> buildInvalidTokenException() {
        Error error = Error.builder()
                .code("400")
                .message("This token doesn't exist.")
                .description("Please use a valid token.")
                .build();
        return Collections.singletonList(error);
    }
}

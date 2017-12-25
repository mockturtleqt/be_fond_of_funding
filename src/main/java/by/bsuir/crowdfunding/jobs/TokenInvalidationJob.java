package by.bsuir.crowdfunding.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import by.bsuir.crowdfunding.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenInvalidationJob {

    private final VerificationTokenService tokenVerificationService;

    @Autowired
    public TokenInvalidationJob(VerificationTokenService tokenVerificationService) {
        this.tokenVerificationService = tokenVerificationService;
    }

    @Scheduled(cron = "${token.invalidation.job.period}")
    public void run() {
        log.debug("Starting job that invalidates registration tokens");
        try {
            tokenVerificationService.invalidateTokens();
        } catch (Throwable e) {
            log.error("Error when invalidating tokens", e);
        }
    }
}
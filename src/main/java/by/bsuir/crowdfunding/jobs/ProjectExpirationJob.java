package by.bsuir.crowdfunding.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import by.bsuir.crowdfunding.service.ProjectExpirationService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProjectExpirationJob {

    private final ProjectExpirationService projectExpirationService;

    @Autowired
    public ProjectExpirationJob(ProjectExpirationService projectExpirationService) {
        this.projectExpirationService = projectExpirationService;
    }

    @Scheduled(cron = "${project.expiration.job.period}")
    public void run() {
        log.debug("Starting job that invalidates projects");
        try {
            projectExpirationService.expireProjects();
        } catch (Throwable e) {
            log.error("Error when invalidating projects", e);
        }
    }
}

package by.bsuir.crowdfunding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.FundingInfoRepository;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectExpirationService {

    private final ProjectRepository projectRepository;
    private final FundingInfoRepository fundingInfoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectExpirationService(ProjectRepository projectRepository, FundingInfoRepository fundingInfoRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.fundingInfoRepository = fundingInfoRepository;
        this.userRepository = userRepository;
    }

    public void expireProjects() {
        List<Project> expiredProjects = projectRepository.findByDueDateBeforeAndIsActive(Timestamp.valueOf(LocalDateTime.now()), true);
        for (Project project : expiredProjects) {
            if (isNecessarySumRaised(project)) {
                sendMoneyToProjectCreator(project);
            } else {
                returnMoneyToFunders(project.getId());
            }
            project.setIsActive(false);
        }
        projectRepository.save(expiredProjects);
    }

    private void sendMoneyToProjectCreator(Project project) {
        User projectCreator = increaseBalance(project.getUser(), project.getActualMoneyAmount());
        userRepository.save(projectCreator);
    }

    private void returnMoneyToFunders(long projectId) {
        List<User> funders = fundingInfoRepository.findFundingInfoByProjectId(projectId)
                .stream()
                .map(fundingInfo -> increaseBalance(fundingInfo.getUser(), fundingInfo.getAmountOfMoney()))
                .collect(Collectors.toList());
        userRepository.save(funders);
    }

    private boolean isNecessarySumRaised(Project project) {
        return project.getActualMoneyAmount().compareTo(project.getMinimalMoneyAmount()) >= 0;
    }

    private User increaseBalance(User user, BigDecimal sumToIncreaseBy) {
        BigDecimal currentUserBalance = user.getBalance();
        user.setBalance(currentUserBalance.add(sumToIncreaseBy));
        return user;
    }
}

package by.bsuir.crowdfunding.service;


import by.bsuir.crowdfunding.exception.NotEnoughMoneyException;
import by.bsuir.crowdfunding.exception.ValueNotFoundException;
import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.FundingInfoRepository;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.Error;
import by.bsuir.crowdfunding.rest.FundingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.bsuir.crowdfunding.utils.ExceptionBuilder.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FundingService {

    private final FundingInfoRepository fundingInfoRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public FundingService(FundingInfoRepository fundingInfoRepository,
                          ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.fundingInfoRepository = fundingInfoRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public FundingInfo fundProject(FundingDto fundingDto) throws NotEnoughMoneyException, ValueNotFoundException {
        User user = userRepository.findOne(fundingDto.getUserId());
        Project project = projectRepository.findOne(fundingDto.getProjectId());
        if (nonNull(user) && nonNull(project)) {
            if (user.getBalance().compareTo(fundingDto.getAmountOfMoney()) >= 0) {
                updateUserBalance(user, fundingDto.getAmountOfMoney());
                updateProjectBalance(project, fundingDto.getAmountOfMoney());
                return fundingInfoRepository.save(convertToModelFromDto(fundingDto, user, project));
            } else {
                throw new NotEnoughMoneyException(Collections.singletonList(buildNotEnoughMoneyError()));
            }
        }
        List<Error> errors = new ArrayList<>();
        if (isNull(user)) {
            errors.add(buildNoSuchUserException(fundingDto.getUserId()));
        }
        if (isNull(project)) {
            errors.add(buildNoSuchProjectException(fundingDto.getProjectId()));
        }
        if (!errors.isEmpty()) {
            throw new ValueNotFoundException(errors);
        }
        return null;
    }

    public List<FundingInfo> findFundingInfoByProject(Long projectId) {
        return fundingInfoRepository.findFundingInfoByProjectId(projectId);
    }

    public List<FundingInfo> findFundingInfoByUser(Long userId) {
        return fundingInfoRepository.findFundingInfoByUserId(userId);
    }

    private FundingInfo convertToModelFromDto(FundingDto fundingDto, User user, Project project) {
        return FundingInfo.builder()
                .project(project)
                .user(user)
                .amountOfMoney(fundingDto.getAmountOfMoney())
                .dateOfPayment(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    private void updateUserBalance(User user, BigDecimal amountOfMoney) {
        user.setBalance(user.getBalance().subtract(amountOfMoney));
        userRepository.save(user);
    }

    private void updateProjectBalance(Project project, BigDecimal amountOfMoney) {
        project.setActualMoneyAmount(project.getActualMoneyAmount().add(amountOfMoney));
        projectRepository.save(project);
    }
}

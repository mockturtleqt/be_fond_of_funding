package by.bsuir.crowdfunding.service;

import by.bsuir.crowdfunding.converter.ProjectConverter;
import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.ProjectDto;
import by.bsuir.crowdfunding.utils.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectConverter projectConverter;
    private final FundingService fundingService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          ProjectConverter projectConverter,
                          FundingService fundingService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectConverter = projectConverter;
        this.fundingService = fundingService;
    }

    public Project createProject(ProjectDto projectDto) {
        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .dueDate(ConverterUtils.convertLocalDateToTimestamp(projectDto.getDueDate()))
                .minimalMoneyAmount(projectDto.getMinimalMoneyAmount())
                .actualMoneyAmount(BigDecimal.ZERO)
                .picture(projectDto.getPicture())
                .additionalInfo(projectDto.getAdditionalInfo())
                .user(userRepository.findOne(projectDto.getUserId()))
                .isActive(false)
                .isApproved(false)
                .build();
        return projectRepository.save(project);
    }

    public Project updateProject(ProjectDto projectDto) {
        Project project = projectRepository.findOne(projectDto.getProjectId());
        if (nonNull(project)) {
            project.setName(projectDto.getName());
            project.setDescription(projectDto.getDescription());
            project.setAdditionalInfo(projectDto.getAdditionalInfo());
            project.setDueDate(ConverterUtils.convertLocalDateToTimestamp(projectDto.getDueDate()));
            project.setMinimalMoneyAmount(projectDto.getMinimalMoneyAmount());
            project.setPicture(projectDto.getPicture());
            return projectRepository.save(project);
        } else return null;
    }

    public List<ProjectDto> findActiveProjectsByName(String name) {
        return projectConverter.convertModelToDto(projectRepository.findAllByNameContainingAndIsActive(name, true));
    }

    public List<ProjectDto> findAllActiveProjects() {
        return projectConverter.convertModelToDto(projectRepository.findAllByIsActive(true));
    }

    public List<ProjectDto> findAllNonApprovedProjects() {
        return projectConverter.convertModelToDto(projectRepository.findAllByIsApproved(false));
    }

    public List<Project> findNonApproved() {
        return projectRepository.findAllByIsApproved(false);
    }

    public List<Project> approveProject(List<Long> projectIds) {
        List<Project> projects = (List<Project>) projectRepository.findAll(projectIds);
        projects.forEach(this::approve);
        return (List<Project>) projectRepository.save(projects);
    }

    public List<Project> findApprovedProjectsByAuthorId(Long authorId) {
        return projectRepository.findAllByUserId(authorId)
                .stream()
                .filter(Project::getIsApproved)
                .collect(Collectors.toList());
    }

    public List<Project> findNonApprovedProjectsByAuthorId(Long authorId) {
        return projectRepository.findAllByUserId(authorId)
                .stream()
                .filter(project -> !project.getIsApproved())
                .collect(Collectors.toList());
    }

    public Set<Project> findProjectsByFinancerId(Long financerId) {
        return fundingService.findFundingInfoByUser(financerId)
                .stream()
                .map(FundingInfo::getProject)
                .collect(Collectors.toSet());
    }

    private Project approve(Project project) {
        project.setIsApproved(true);
        project.setIsActive(true);
        return project;
    }
}

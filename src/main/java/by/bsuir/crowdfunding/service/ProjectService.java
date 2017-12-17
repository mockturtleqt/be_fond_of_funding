package by.bsuir.crowdfunding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import by.bsuir.crowdfunding.converter.ProjectConverter;
import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.ProjectDto;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectConverter projectConverter;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          ProjectConverter projectConverter) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectConverter = projectConverter;
    }

    public Project createProject(ProjectDto projectDto) {
        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .dueDate(Timestamp.valueOf(LocalDateTime.of(projectDto.getDueDate(), LocalTime.MIDNIGHT)))
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

    private Project approve(Project project) {
        project.setIsApproved(true);
        project.setIsActive(true);
        return project;
    }
}

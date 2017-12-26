package by.bsuir.crowdfunding.converter;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.rest.ProjectDto;

@Component
public class ProjectConverter implements Converter<Project, ProjectDto> {

    @Override
    public ProjectDto convertModelToDto(Project project) {
        return ProjectDto.builder()
                .projectId(project.getId())
                .userId(project.getUser().getId())
                .name(project.getName())
                .description(project.getDescription())
                .dueDate(project.getDueDate().toLocalDateTime().toLocalDate())
                .minimalMoneyAmount(project.getMinimalMoneyAmount())
                .actualMoneyAmount(project.getActualMoneyAmount())
                .picture(project.getPicture())
                .additionalInfo(project.getAdditionalInfo())
                .build();
    }

    public List<ProjectDto> convertModelToDto(List<Project> projects) {
        return projects
                .stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }
}

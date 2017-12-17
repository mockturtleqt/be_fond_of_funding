package by.bsuir.crowdfunding.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.rest.ProjectDto;
import by.bsuir.crowdfunding.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@CrossOrigin
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to create a project.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project was created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/user/project/createProject")
    public void createProject(@RequestBody @Valid @ApiParam(value = "request") ProjectDto projectDto) {
        projectService.createProject(projectDto);
    }

    @ResponseStatus(OK)
    @ApiOperation("Get active projects by name.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project info", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/project/findProjectByName")
    public List<ProjectDto> findProjectByName(@RequestParam(name = "name") String name) {
        return projectService.findActiveProjectsByName(name);
    }

    @ResponseStatus(OK)
    @ApiOperation("Get all non approved projects.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Non approved projects", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/admin/project/findNonApprovedProjects")
    public List<ProjectDto> findNonApprovedProjects() {
        return projectService.findAllNonApprovedProjects();
    }

    @ResponseStatus(OK)
    @ApiOperation("Get all non approved projects.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Non approved projects", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/admin/project/findNonApproved")
    public List<Project> findNonApproved() {
        return projectService.findNonApproved();
    }
}

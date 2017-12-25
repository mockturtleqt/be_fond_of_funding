package by.bsuir.crowdfunding.controller;


import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.rest.ProjectDto;
import by.bsuir.crowdfunding.service.ProjectService;
import by.bsuir.crowdfunding.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@CrossOrigin
@RestController
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to create a project.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project was created", response = String.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/user/project/createProject")
    public void createProject(@RequestBody @Valid @ApiParam(value = "request") ProjectDto projectDto) {
        projectService.createProject(projectDto);
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to update a project.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project was updated", response = String.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/user/project/updateProject")
    public void updateProject(@RequestBody @Valid @ApiParam(value = "request") ProjectDto projectDto) {
        projectService.updateProject(projectDto);
    }

    @ResponseStatus(OK)
    @ApiOperation("Link to approve projects (arrray of project ids should be passed as parameters).")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project was approved", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = POST, path = "/admin/project/approveProjects")
    public void approveProject(@RequestBody @ApiParam(value = "request") List<Long> idsOfProjects) {
        projectService.approveProject(idsOfProjects);
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
    @ApiOperation("Get active projects.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Project info", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/project/findActiveProjects")
    public List<ProjectDto> findActiveProjects() {
        return projectService.findAllActiveProjects();
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
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Non approved projects", response = Project.class),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/admin/project/findNonApproved")
    public List<Project> findNonApproved() {
        return projectService.findNonApproved();
    }

    @ResponseStatus(OK)
    @ApiOperation("Get users by project they financed.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User info", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request parameters.", response = Error.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Unexpected error.", response = Error.class, responseContainer = "List")})
    @RequestMapping(method = GET, path = "/findFundersByProject")
    public Set<User> findFundersByProject(@RequestParam(name = "projectId") Long projectId) {
        return userService.findFundersByProejctId(projectId);
    }
}

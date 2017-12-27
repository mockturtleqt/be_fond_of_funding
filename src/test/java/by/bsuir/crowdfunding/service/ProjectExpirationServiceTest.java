package by.bsuir.crowdfunding.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.FundingInfoRepository;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectExpirationServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private FundingInfoRepository fundingInfoRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ProjectExpirationService projectExpirationService;

    @Test
    public void shouldTransferMoneyToAuthorIfSumIsRaised() throws Exception {
        User user = User.builder()
                .balance(BigDecimal.ONE)
                .build();
        List<Project> projects = Arrays.asList(buildProject(user), buildProject(user));
        when(projectRepository.findByDueDateBeforeAndIsActive(any(Timestamp.class), eq(true))).thenReturn(projects);
        projectExpirationService.expireProjects();
        assertEquals(false, projects.get(0).getIsActive());
        assertEquals(false, projects.get(1).getIsActive());
        assertEquals(BigDecimal.valueOf(21), user.getBalance());
    }

    private Project buildProject(User user) {
        return Project.builder()
                .isActive(true)
                .actualMoneyAmount(BigDecimal.TEN)
                .minimalMoneyAmount(BigDecimal.ONE)
                .user(user)
                .build();
    }

}
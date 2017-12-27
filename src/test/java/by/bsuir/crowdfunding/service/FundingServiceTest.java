package by.bsuir.crowdfunding.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import by.bsuir.crowdfunding.exception.NotEnoughMoneyException;
import by.bsuir.crowdfunding.exception.ValueNotFoundException;
import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.model.Project;
import by.bsuir.crowdfunding.model.User;
import by.bsuir.crowdfunding.repository.FundingInfoRepository;
import by.bsuir.crowdfunding.repository.ProjectRepository;
import by.bsuir.crowdfunding.repository.UserRepository;
import by.bsuir.crowdfunding.rest.FundingDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FundingServiceTest {

    @Mock
    private FundingInfoRepository fundingInfoRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FundingService fundingService;

    @Test
    public void shouldTransferMoneyToProjectIfEnough() throws Exception {
        User user = User.builder()
                .id(1l)
                .balance(BigDecimal.valueOf(10))
                .build();
        Project project = Project.builder()
                .id(1L)
                .actualMoneyAmount(BigDecimal.valueOf(1))
                .build();
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(projectRepository.findOne(anyLong())).thenReturn(project);

        FundingDto fundingDto = FundingDto.builder()
                .userId(1L)
                .projectId(1L)
                .amountOfMoney(BigDecimal.valueOf(5))
                .build();
        fundingService.fundProject(fundingDto);
        assertEquals(BigDecimal.valueOf(5), user.getBalance());
        assertEquals(BigDecimal.valueOf(6), project.getActualMoneyAmount());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldNotTransferMoneyIfNotEnough() throws Exception {
        User user = User.builder()
                .id(1l)
                .balance(BigDecimal.valueOf(0))
                .build();
        Project project = Project.builder()
                .id(1L)
                .actualMoneyAmount(BigDecimal.valueOf(1))
                .build();
        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(projectRepository.findOne(anyLong())).thenReturn(project);

        FundingDto fundingDto = FundingDto.builder()
                .userId(1L)
                .projectId(1L)
                .amountOfMoney(BigDecimal.valueOf(5))
                .build();
        fundingService.fundProject(fundingDto);
        assertEquals(BigDecimal.valueOf(0), user.getBalance());
        assertEquals(BigDecimal.valueOf(1), project.getActualMoneyAmount());
    }

    @Test(expected = ValueNotFoundException.class)
    public void shouldThrowExceptionIfUserDoesntExist() throws Exception {
        Project project = Project.builder()
                .id(1L)
                .actualMoneyAmount(BigDecimal.valueOf(1))
                .build();
        when(projectRepository.findOne(anyLong())).thenReturn(project);
        when(userRepository.findOne(anyLong())).thenReturn(null);

        FundingDto fundingDto = FundingDto.builder()
                .userId(1L)
                .projectId(1L)
                .amountOfMoney(BigDecimal.valueOf(5))
                .build();
        fundingService.fundProject(fundingDto);
    }

    @Test(expected = ValueNotFoundException.class)
    public void shouldThrowExceptionIfProjectDoesntExist() throws Exception {
        User user = User.builder()
                .id(1l)
                .balance(BigDecimal.valueOf(10))
                .build();

        when(userRepository.findOne(anyLong())).thenReturn(user);
        when(projectRepository.findOne(anyLong())).thenReturn(null);

        FundingDto fundingDto = FundingDto.builder()
                .userId(1L)
                .projectId(1L)
                .amountOfMoney(BigDecimal.valueOf(5))
                .build();
        fundingService.fundProject(fundingDto);
    }

    @Test
    public void shoudFindFundingInfoByProjectId() {
        List<FundingInfo> fundingInfos = Collections.singletonList(new FundingInfo());
        when(fundingInfoRepository.findFundingInfoByProjectId(anyLong())).thenReturn(fundingInfos);
        assertEquals(fundingInfos, fundingService.findFundingInfoByProject(1L));
    }

    @Test
    public void shoudFindFundingInfoByUserId() {
        List<FundingInfo> fundingInfos = Collections.singletonList(new FundingInfo());
        when(fundingInfoRepository.findFundingInfoByUserId(anyLong())).thenReturn(fundingInfos);
        assertEquals(fundingInfos, fundingService.findFundingInfoByUser(1L));
    }
}
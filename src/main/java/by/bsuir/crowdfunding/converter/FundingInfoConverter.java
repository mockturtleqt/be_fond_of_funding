package by.bsuir.crowdfunding.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.rest.FundingDto;

@Component
public class FundingInfoConverter {

//    public FundingInfo convertToModelFromDto(FundingDto dto) {
//        FundingInfo fundingInfo = FundingInfo.builder()
//                .project(projectRepository.findOne(fundingDto.getProjectId()))
//                .user(userRepository.findOne(fundingDto.getUserId()))
//                .amountOfMoney(fundingDto.getAmountOfMoney())
//                .dateOfPayment(LocalDateTime.now())
//                .build();
//    }
}

package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import by.bsuir.crowdfunding.model.FundingInfo;
import by.bsuir.crowdfunding.model.Project;

public interface FundingInfoRepository extends CrudRepository<FundingInfo, Long> {
}

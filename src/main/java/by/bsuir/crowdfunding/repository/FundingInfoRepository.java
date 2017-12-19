package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import by.bsuir.crowdfunding.model.FundingInfo;

@Repository
public interface FundingInfoRepository extends CrudRepository<FundingInfo, Long> {

    List<FundingInfo> findFundingInfoByProjectId(long projectId);
}

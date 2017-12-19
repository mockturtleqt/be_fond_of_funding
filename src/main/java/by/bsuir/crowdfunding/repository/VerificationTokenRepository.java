package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import by.bsuir.crowdfunding.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    List<VerificationToken> findByExpirationDateBeforeAndIsActive(Timestamp now, boolean isActive);

    VerificationToken findByUserId(long userId);

    VerificationToken findByToken(String token);
}

package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import by.bsuir.crowdfunding.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByLoginAndEnabled(String login, boolean enabled);

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}

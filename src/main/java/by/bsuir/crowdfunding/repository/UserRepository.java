package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;

import by.bsuir.crowdfunding.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByLogin(String login);
}

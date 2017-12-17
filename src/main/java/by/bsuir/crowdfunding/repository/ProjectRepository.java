package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import by.bsuir.crowdfunding.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project> findAllByNameContainingAndIsActive(String name, Boolean isActive);

    List<Project> findAllByIsActive(Boolean isActive);

    List<Project> findAllByIsApproved(Boolean isApproved);
}

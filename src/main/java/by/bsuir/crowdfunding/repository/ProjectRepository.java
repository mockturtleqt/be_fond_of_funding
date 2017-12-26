package by.bsuir.crowdfunding.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import by.bsuir.crowdfunding.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project> findAllByNameContainingAndIsActive(String name, Boolean isActive);

    List<Project> findAllByIsActive(Boolean isActive);

    List<Project> findAllByIsApproved(Boolean isApproved);

    List<Project> findByDueDateBeforeAndIsActive(Timestamp now, boolean isActive);

    List<Project> findAllByUserId(Long userId);

    List<Project> findByName(String name);
}

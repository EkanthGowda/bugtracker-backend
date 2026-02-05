package com.bugtracker.repository;

import com.bugtracker.model.Issue;
import com.bugtracker.model.IssuePriority;
import com.bugtracker.model.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByProjectId(Long projectId);

    List<Issue> findByAssigneeEmail(String email);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByPriority(IssuePriority priority);

    List<Issue> findByProjectIdAndStatus(Long projectId, IssueStatus status);

    List<Issue> findByProjectIdAndPriority(Long projectId, IssuePriority priority);
}

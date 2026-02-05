package com.bugtracker.service;

import com.bugtracker.model.*;
import com.bugtracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Issue createIssue(
            Issue issue,
            @NonNull Long projectId,
            String creatorEmail) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        issue.setProject(project);
        issue.setStatus(IssueStatus.TODO);
        issue.setCreatedAt(LocalDateTime.now());

        // auto assign if provided
        if (issue.getAssignee() != null) {
            User assignee = userRepository.findById(
                    issue.getAssignee().getId()
            ).orElseThrow(() -> new RuntimeException("Assignee not found"));
            issue.setAssignee(assignee);
        }

        return issueRepository.save(issue);
    }

    public List<Issue> getIssuesByProject(@NonNull Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public List<Issue> getMyIssues(String email) {
        return issueRepository.findByAssigneeEmail(email);
    }

    public Issue updateStatus(@NonNull Long issueId, IssueStatus status) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setStatus(status);
        return issueRepository.save(issue);
    }

    public List<Issue> filterByStatus(IssueStatus status) {
        return issueRepository.findByStatus(status);
    }

    public List<Issue> filterByPriority(IssuePriority priority) {
        return issueRepository.findByPriority(priority);
    }

    public List<Issue> filterByAssignee(String email) {
        return issueRepository.findByAssigneeEmail(email);
    }

    public List<Issue> filterByProjectAndStatus(Long projectId, IssueStatus status) {
        return issueRepository.findByProjectIdAndStatus(projectId, status);
    }

    public List<Issue> filterByProjectAndPriority(Long projectId, IssuePriority priority) {
        return issueRepository.findByProjectIdAndPriority(projectId, priority);
    }

    public Issue assignIssue(@NonNull Long issueId, @NonNull Long userId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        issue.setAssignee(assignee);
        return issueRepository.save(issue);
    }

    public Issue getIssueById(@NonNull Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }
}

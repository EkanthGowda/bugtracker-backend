package com.bugtracker.controller;

import com.bugtracker.model.Issue;
import com.bugtracker.model.IssueStatus;
import com.bugtracker.model.IssuePriority;
import com.bugtracker.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/project/{projectId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Issue createIssue(
            @PathVariable Long projectId,
            @RequestBody Issue issue,
            Authentication authentication) {

        return issueService.createIssue(
                issue,
                projectId,
                authentication.getName()
        );
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public List<Issue> getIssuesByProject(
            @PathVariable Long projectId) {

        return issueService.getIssuesByProject(projectId);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('ROLE_DEVELOPER','ROLE_MANAGER','ROLE_ADMIN')")
    public List<Issue> getMyIssues(Authentication authentication) {
        return issueService.getMyIssues(authentication.getName());
    }

    @PutMapping("/{issueId}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public Issue updateStatus(
            @PathVariable Long issueId,
            @RequestParam IssueStatus status) {

        return issueService.updateStatus(issueId, status);
    }

    @PutMapping("/{issueId}/assign/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public Issue assignIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId) {

        return issueService.assignIssue(issueId, userId);
    }

    @GetMapping("/{issueId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public Issue getIssueById(@PathVariable Long issueId) {
        return issueService.getIssueById(issueId);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public List<Issue> filterIssues(
            @RequestParam(required = false) IssueStatus status,
            @RequestParam(required = false) IssuePriority priority,
            @RequestParam(required = false) String assignee) {

        if (status != null) {
            return issueService.filterByStatus(status);
        }
        if (priority != null) {
            return issueService.filterByPriority(priority);
        }
        if (assignee != null) {
            return issueService.filterByAssignee(assignee);
        }

        return List.of();
    }
}

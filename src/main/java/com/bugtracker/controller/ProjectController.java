package com.bugtracker.controller;

import com.bugtracker.model.Project;
import com.bugtracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public Project createProject(
            @RequestBody Project project,
            Authentication authentication) {

        return projectService.createProject(
                project,
                authentication.getName()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping("/{projectId}/add-member/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_DEVELOPER')")
    public Project addMember(
            @PathVariable Long projectId,
            @PathVariable Long userId) {

        return projectService.addMember(projectId, userId);
    }
}

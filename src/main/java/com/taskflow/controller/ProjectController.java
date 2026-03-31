package com.taskflow.controller;

import com.taskflow.domain.User;
import com.taskflow.dto.request.ProjectRequests.*;
import com.taskflow.dto.response.Responses.*;
import com.taskflow.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "Create and manage projects within teams")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project")
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return projectService.createProject(request, currentUser);
    }

    @GetMapping
    @Operation(summary = "Get all projects accessible to the current user")
    public PageResponse<ProjectResponse> getMyProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal User currentUser
    ) {
        return projectService.getMyProjects(currentUser, page, size);
    }

    @GetMapping("/team/{teamId}")
    @Operation(summary = "Get all projects for a specific team")
    public PageResponse<ProjectResponse> getProjectsByTeam(
            @PathVariable UUID teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal User currentUser
    ) {
        return projectService.getProjectsByTeam(teamId, currentUser, page, size);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a specific project with progress info")
    public ProjectResponse getProject(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User currentUser
    ) {
        return projectService.getProject(projectId, currentUser);
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update project details")
    public ProjectResponse updateProject(
            @PathVariable UUID projectId,
            @Valid @RequestBody UpdateProjectRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return projectService.updateProject(projectId, request, currentUser);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a project")
    public void deleteProject(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User currentUser
    ) {
        projectService.deleteProject(projectId, currentUser);
    }
}

package com.taskflow.service;

import com.taskflow.domain.User;
import com.taskflow.dto.request.ProjectRequests.*;
import com.taskflow.dto.response.Responses.*;

import java.util.UUID;

public interface ProjectService {
    ProjectResponse createProject(CreateProjectRequest request, User currentUser);
    ProjectResponse getProject(UUID projectId, User currentUser);
    PageResponse<ProjectResponse> getProjectsByTeam(UUID teamId, User currentUser, int page, int size);
    PageResponse<ProjectResponse> getMyProjects(User currentUser, int page, int size);
    ProjectResponse updateProject(UUID projectId, UpdateProjectRequest request, User currentUser);
    void deleteProject(UUID projectId, User currentUser);
}

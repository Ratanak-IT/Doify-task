package com.taskflow.service;

import com.taskflow.domain.User;
import com.taskflow.dto.request.TeamRequests.*;
import com.taskflow.dto.response.Responses.*;

import java.util.UUID;

public interface TeamService {
    TeamResponse createTeam(CreateTeamRequest request, User currentUser);
    TeamResponse getTeam(UUID teamId, User currentUser);
    PageResponse<TeamResponse> getMyTeams(User currentUser, int page, int size);
    TeamResponse updateTeam(UUID teamId, UpdateTeamRequest request, User currentUser);
    void deleteTeam(UUID teamId, User currentUser);
    void acceptInvitationById(UUID invitationId, User currentUser);
    void inviteMember(UUID teamId, InviteMemberRequest request, User currentUser);
    void acceptInvitation(String token, User currentUser);
    PageResponse<TeamMemberResponse> getMembers(UUID teamId, User currentUser, int page, int size);
    void updateMemberRole(UUID teamId, UUID memberId, UpdateMemberRoleRequest request, User currentUser);
    void removeMember(UUID teamId, UUID userId, User currentUser);
}

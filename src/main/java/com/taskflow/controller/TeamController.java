package com.taskflow.controller;

import com.taskflow.domain.User;
import com.taskflow.dto.request.TeamRequests.*;
import com.taskflow.dto.response.Responses.*;
import com.taskflow.service.TeamService;
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
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "Create teams, manage members and invitations")
@SecurityRequirement(name = "bearerAuth")
public class TeamController {

    private final TeamService teamService;

    // ─── Team CRUD ───────────────────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new team")
    public TeamResponse createTeam(
            @Valid @RequestBody CreateTeamRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return teamService.createTeam(request, currentUser);
    }

    @GetMapping
    @Operation(summary = "Get all teams the current user belongs to")
    public PageResponse<TeamResponse> getMyTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal User currentUser
    ) {
        return teamService.getMyTeams(currentUser, page, size);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "Get a specific team")
    public TeamResponse getTeam(
            @PathVariable UUID teamId,
            @AuthenticationPrincipal User currentUser
    ) {
        return teamService.getTeam(teamId, currentUser);
    }

    @PutMapping("/{teamId}")
    @Operation(summary = "Update team details")
    public TeamResponse updateTeam(
            @PathVariable UUID teamId,
            @Valid @RequestBody UpdateTeamRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return teamService.updateTeam(teamId, request, currentUser);
    }

    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a team (Owner only)")
    public void deleteTeam(
            @PathVariable UUID teamId,
            @AuthenticationPrincipal User currentUser
    ) {
        teamService.deleteTeam(teamId, currentUser);
    }

    // ─── Members ─────────────────────────────────────────────────────────────

    @GetMapping("/{teamId}/members")
    @Operation(summary = "List all team members")
    public PageResponse<TeamMemberResponse> getMembers(
            @PathVariable UUID teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @AuthenticationPrincipal User currentUser
    ) {
        return teamService.getMembers(teamId, currentUser, page, size);
    }

    @PatchMapping("/{teamId}/members/{memberId}/role")
    @Operation(summary = "Update a member's role")
    public MessageResponse updateMemberRole(
            @PathVariable UUID teamId,
            @PathVariable UUID memberId,
            @Valid @RequestBody UpdateMemberRoleRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        teamService.updateMemberRole(teamId, memberId, request, currentUser);
        return new MessageResponse("Member role updated successfully");
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove a member from the team")
    public void removeMember(
            @PathVariable UUID teamId,
            @PathVariable UUID userId,
            @AuthenticationPrincipal User currentUser
    ) {
        teamService.removeMember(teamId, userId, currentUser);
    }

    // ─── Invitations ─────────────────────────────────────────────────────────

    @PostMapping("/{teamId}/invitations")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Invite a member to the team via email")
    public MessageResponse inviteMember(
            @PathVariable UUID teamId,
            @Valid @RequestBody InviteMemberRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        teamService.inviteMember(teamId, request, currentUser);
        return new MessageResponse("Invitation sent to " + request.email());
    }

    @GetMapping("/invitations/accept")
    @Operation(summary = "Accept a team invitation using the token from email")
    public MessageResponse acceptInvitation(
            @RequestParam String token,
            @AuthenticationPrincipal User currentUser
    ) {
        teamService.acceptInvitation(token, currentUser);
        return new MessageResponse("You have successfully joined the team");
    }
}

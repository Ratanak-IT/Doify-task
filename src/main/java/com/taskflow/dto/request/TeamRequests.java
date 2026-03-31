package com.taskflow.dto.request;

import com.taskflow.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TeamRequests {

    public record CreateTeamRequest(
            @NotBlank(message = "Team name is required")
            @Size(max = 100, message = "Team name must not exceed 100 characters")
            String name,

            String description
    ) {}

    public record UpdateTeamRequest(
            @Size(max = 100)
            String name,

            String description
    ) {}

    public record InviteMemberRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email,

            @NotNull(message = "Role is required")
            Role role
    ) {}

    public record UpdateMemberRoleRequest(
            @NotNull(message = "Role is required")
            Role role
    ) {}
}

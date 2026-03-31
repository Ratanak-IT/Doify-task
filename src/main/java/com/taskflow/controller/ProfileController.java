package com.taskflow.controller;

import com.taskflow.domain.User;
import com.taskflow.dto.request.ProfileRequests.*;
import com.taskflow.dto.response.ApiSuccessResponse;
import com.taskflow.dto.response.Responses.UserResponse;
import com.taskflow.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "View and update user profile")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "Get current user profile")
    public ApiSuccessResponse<UserResponse> getProfile(@AuthenticationPrincipal User currentUser) {
        return ApiSuccessResponse.ok(
                "Profile fetched successfully",
                profileService.getProfile(currentUser)
        );
    }

    @PutMapping
    @Operation(summary = "Update profile (name, username, email, profile photo URL)")
    public ApiSuccessResponse<UserResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return ApiSuccessResponse.ok(
                "Profile updated successfully",
                profileService.updateProfile(request, currentUser)
        );
    }

    @PatchMapping("/password")
    @Operation(summary = "Change password")
    public ApiSuccessResponse<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        profileService.changePassword(request, currentUser);
        return ApiSuccessResponse.ok("Password changed successfully");
    }
}
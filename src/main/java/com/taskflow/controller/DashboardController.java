package com.taskflow.controller;

import com.taskflow.domain.User;
import com.taskflow.dto.response.Responses.*;
import com.taskflow.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Summary stats, upcoming tasks, and project progress")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Get full dashboard data for the current user")
    public DashboardResponse getDashboard(@AuthenticationPrincipal User currentUser) {
        return dashboardService.getDashboard(currentUser);
    }
}

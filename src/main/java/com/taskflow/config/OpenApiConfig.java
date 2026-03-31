package com.taskflow.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TaskFlow API",
                version = "1.0.0",
                description = """
                        Full-featured Task & Team Management REST API.

                        Features:
                        - JWT Authentication (Register, Login, Logout, Forgot/Reset Password)
                        - Google OAuth2 Sign-In
                        - Personal Task Management (Create, Update, Delete, Search, Filter, Subtasks)
                        - Team Management (Create, Invite via Email, Accept Invitation, Roles)
                        - Project Management (Create, Progress tracking)
                        - Project Task Management (Assign, Attachments)
                        - Comments with @mention support
                        - Website & Email Notifications
                        - Dashboard with stats and progress
                        """,
                contact = @Contact(name = "TaskFlow Team", email = "support@taskflow.com")
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {}

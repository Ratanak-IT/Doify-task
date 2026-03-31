# TaskFlow API

A full-featured **Task & Team Management REST API** built with Spring Boot 3, Java 21, and PostgreSQL.

---

## Features

| Module | Endpoints |
|---|---|
| **Authentication** | Register, Login, Logout, Refresh Token, Forgot/Reset Password, Email Verify, Google OAuth2 |
| **Personal Tasks** | Create, Update, Delete, Search, Filter by status/priority, Subtasks |
| **Team Management** | Create Team, Invite via Email, Accept Invitation, View/Remove Members, Roles (Owner/Admin/Member) |
| **Project Management** | Create, Update, Delete Project, View Progress (% complete) |
| **Project Tasks** | Create, Assign, Filter, Attachments |
| **Comments** | Add/Edit/Delete, @mention support with notifications |
| **Notifications** | Website notifications (unread count, mark read), Email notifications |
| **Dashboard** | Total stats, Upcoming due dates, Overdue tasks, Project progress summary |
| **Profile** | View/Update name, username, email, photo, Change password |

---

## Tech Stack

- **Java 21** + **Spring Boot 3.3.5**
- **Spring Security** (JWT + OAuth2 Google)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL**
- **Lombok**, **MapStruct**
- **SpringDoc OpenAPI** (Swagger UI)
- **Spring Mail** (async email via `@Async`)
- **Spring Scheduler** (due-date reminders, overdue alerts)

---

## Project Structure

```
src/main/java/com/taskflow/
├── config/           # SecurityConfig, AppConfig, OpenApiConfig
├── controller/       # AuthController, TaskController, TeamController,
│                     # ProjectController, CommentController,
│                     # NotificationController, DashboardController, ProfileController
├── domain/           # JPA entities: User, Team, TeamMember, TeamInvitation,
│   └── enums/        # Project, Task, Comment, Attachment, Notification, RefreshToken
├── dto/
│   ├── request/      # AuthRequests, TaskRequests, TeamRequests, ProjectRequests, …
│   └── response/     # Responses (all response records in one file)
├── exception/        # GlobalExceptionHandler + custom exceptions
├── repository/       # Spring Data JPA repositories with custom JPQL
├── security/         # JwtService, JwtAuthenticationFilter
└── service/
    └── impl/         # AuthServiceImpl, TeamServiceImpl, ProjectServiceImpl,
                      # TaskServiceImpl + EmailService, NotificationService,
                      # ProfileService, DashboardService, SchedulerService
```

---

## Quick Start

### 1. Prerequisites

- Java 21
- PostgreSQL 14+
- Gradle 8+

### 2. Create the database

```sql
CREATE DATABASE taskflow_db;
```

### 3. Configure environment

Copy and edit the variables in `src/main/resources/application.yaml`:

| Variable | Description |
|---|---|
| `DB_USERNAME` | PostgreSQL username (default: `postgres`) |
| `DB_PASSWORD` | PostgreSQL password (default: `postgres`) |
| `JWT_SECRET` | 64-char hex secret for JWT signing |
| `MAIL_HOST` | SMTP host (e.g. `smtp.gmail.com`) |
| `MAIL_USERNAME` | SMTP email address |
| `MAIL_PASSWORD` | Gmail App Password |
| `GOOGLE_CLIENT_ID` | Google OAuth2 Client ID |
| `GOOGLE_CLIENT_SECRET` | Google OAuth2 Client Secret |
| `FRONTEND_URL` | Frontend base URL for email links |

### 4. Run the application

```bash
./gradlew bootRun
```

### 5. Access Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## API Overview

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login |
| POST | `/api/v1/auth/logout` | Logout (invalidate refresh token) |
| POST | `/api/v1/auth/refresh` | Refresh access token |
| POST | `/api/v1/auth/forgot-password` | Send password reset email |
| POST | `/api/v1/auth/reset-password` | Reset password with token |
| GET  | `/api/v1/auth/verify-email?token=` | Verify email |

### Profile

| Method | Endpoint | Description |
|---|---|---|
| GET  | `/api/v1/profile` | Get current user profile |
| PUT  | `/api/v1/profile` | Update name/username/email |
| POST | `/api/v1/profile/photo` | Upload profile photo |
| PATCH| `/api/v1/profile/password` | Change password |

### Teams

| Method | Endpoint | Description |
|---|---|---|
| POST   | `/api/v1/teams` | Create team |
| GET    | `/api/v1/teams` | Get my teams |
| GET    | `/api/v1/teams/{id}` | Get team |
| PUT    | `/api/v1/teams/{id}` | Update team |
| DELETE | `/api/v1/teams/{id}` | Delete team |
| GET    | `/api/v1/teams/{id}/members` | List members |
| DELETE | `/api/v1/teams/{id}/members/{userId}` | Remove member |
| PATCH  | `/api/v1/teams/{id}/members/{memberId}/role` | Update role |
| POST   | `/api/v1/teams/{id}/invitations` | Invite via email |
| GET    | `/api/v1/teams/invitations/accept?token=` | Accept invitation |

### Projects

| Method | Endpoint | Description |
|---|---|---|
| POST   | `/api/v1/projects` | Create project |
| GET    | `/api/v1/projects` | Get accessible projects |
| GET    | `/api/v1/projects/team/{teamId}` | Projects by team |
| GET    | `/api/v1/projects/{id}` | Get project + progress |
| PUT    | `/api/v1/projects/{id}` | Update project |
| DELETE | `/api/v1/projects/{id}` | Delete project |

### Tasks

| Method | Endpoint | Description |
|---|---|---|
| POST   | `/api/v1/tasks/personal` | Create personal task |
| GET    | `/api/v1/tasks/personal` | List personal tasks (search/filter) |
| POST   | `/api/v1/tasks/project/{projectId}` | Create project task |
| GET    | `/api/v1/tasks/project/{projectId}` | List project tasks (search/filter) |
| GET    | `/api/v1/tasks/{id}` | Get task |
| PUT    | `/api/v1/tasks/{id}` | Update task |
| DELETE | `/api/v1/tasks/{id}` | Delete task |
| GET    | `/api/v1/tasks/{id}/subtasks` | Get subtasks |
| POST   | `/api/v1/tasks/{id}/attachments` | Upload attachment |
| DELETE | `/api/v1/tasks/{id}/attachments/{aid}` | Delete attachment |

### Comments

| Method | Endpoint | Description |
|---|---|---|
| POST   | `/api/v1/tasks/{taskId}/comments` | Add comment (supports @mention) |
| GET    | `/api/v1/tasks/{taskId}/comments` | Get comments |
| PUT    | `/api/v1/tasks/{taskId}/comments/{id}` | Update comment |
| DELETE | `/api/v1/tasks/{taskId}/comments/{id}` | Delete comment |

### Notifications

| Method | Endpoint | Description |
|---|---|---|
| GET   | `/api/v1/notifications` | Get notifications |
| GET   | `/api/v1/notifications/unread-count` | Get unread count |
| PATCH | `/api/v1/notifications/read-all` | Mark all as read |
| PATCH | `/api/v1/notifications/{id}/read` | Mark one as read |

### Dashboard

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/dashboard` | Full dashboard stats |

---

## Notification Types

| Type | Trigger |
|---|---|
| `TASK_ASSIGNED` | Task assigned to a user |
| `DUE_DATE_REMINDER` | Scheduled: day before due date |
| `OVERDUE_TASK` | Scheduled: daily scan for overdue tasks |
| `MENTIONED_IN_COMMENT` | @username in a comment |
| `COMMENT_ADDED` | Comment on a task you created or are assigned to |
| `INVITATION_ACCEPTED` | Invitee accepted your team invitation |
| `TEAM_INVITATION` | You were invited to a team |

---

## Task Status Values

`TODO` → `IN_PROGRESS` → `IN_REVIEW` → `DONE` / `CANCELLED`

## Priority Values

`LOW` | `MEDIUM` | `HIGH` | `URGENT`

## Team Roles

| Role | Permissions |
|---|---|
| `OWNER` | Full control, delete team, transfer |
| `ADMIN` | Invite/remove members, update team, manage projects |
| `MEMBER` | View and work on projects/tasks |

---

## Scheduled Jobs

| Job | Schedule | Action |
|---|---|---|
| Due-date reminders | Daily 8:00 AM | Email + notification for tasks due tomorrow |
| Overdue alerts | Daily 9:00 AM | Notification for all overdue tasks |

---

## License

MIT

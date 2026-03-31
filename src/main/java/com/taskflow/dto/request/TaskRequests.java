package com.taskflow.dto.request;

import com.taskflow.domain.enums.Priority;
import com.taskflow.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class TaskRequests {

    public record CreateTaskRequest(
            @NotBlank(message = "Title is required")
            @Size(max = 200, message = "Title must not exceed 200 characters")
            String title,

            String description,
            Priority priority,
            LocalDate dueDate,
            UUID projectId,
            UUID assigneeId,
            UUID parentTaskId
    ) {}

    public record UpdateTaskRequest(
            @Size(max = 200)
            String title,

            String description,
            Priority priority,
            TaskStatus status,
            LocalDate dueDate,
            UUID assigneeId
    ) {}

    public record TaskFilterRequest(
            String search,
            TaskStatus status,
            Priority priority,
            UUID assigneeId,
            int page,
            int size
    ) {}
}

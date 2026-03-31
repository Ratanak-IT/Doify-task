package com.taskflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {

    public record CreateCommentRequest(
            @NotBlank(message = "Comment content is required")
            String content
    ) {}

    public record UpdateCommentRequest(
            @NotBlank(message = "Comment content is required")
            String content
    ) {}
}

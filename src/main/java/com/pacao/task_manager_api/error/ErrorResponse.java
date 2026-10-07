package com.pacao.task_manager_api.error;

public record ErrorResponse(
        String name,
        String message
) {
}

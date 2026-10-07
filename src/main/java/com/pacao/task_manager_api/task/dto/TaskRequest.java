package com.pacao.task_manager_api.task.dto;

public record TaskRequest(
        Integer id,
        String title,
        String description,
        String statutus,
        String createdAt,
        Integer idUser
) {
}

package com.pacao.task_manager_api.task.dto;

import java.time.LocalDate;

public record TaskRequest(
        Integer id,
        String title,
        String description,
        String statutus,
        LocalDate createdAt,
        Integer idUser
) {
}

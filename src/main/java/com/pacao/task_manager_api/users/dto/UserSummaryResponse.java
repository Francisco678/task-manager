package com.pacao.task_manager_api.users.dto;

import lombok.Builder;

@Builder
public record UserSummaryResponse(
        Integer id,
        String nombre
) {
}

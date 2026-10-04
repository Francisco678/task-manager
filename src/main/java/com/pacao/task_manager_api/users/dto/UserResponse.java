package com.pacao.task_manager_api.users.dto;

import lombok.Builder;

@Builder
public record UserResponse(Integer id, String nombre, String email) {
}

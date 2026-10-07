package com.pacao.task_manager_api.users.dto;

import lombok.Builder;
import com.pacao.task_manager_api.task.dto.TaskResponse;
import java.util.List;

@Builder
public record UserResponse(Integer id, String nombre, String email, List<TaskResponse> task) {
}

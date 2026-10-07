package com.pacao.task_manager_api.users.dto;

import com.pacao.task_manager_api.task.dto.TaskResponse;



public record UserRequest(Integer id, String nombre, String email) {
}

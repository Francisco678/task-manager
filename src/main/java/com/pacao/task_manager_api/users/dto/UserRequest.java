package com.pacao.task_manager_api.users.dto;

import com.pacao.task_manager_api.task.dto.TaskResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public record UserRequest(
        Integer id,
        @NotNull(message = "El nombre de usuario no puede ser null")
        String nombre,
        @Email(message = "El correo tiene que tener un formato valido")
        String email) {
}

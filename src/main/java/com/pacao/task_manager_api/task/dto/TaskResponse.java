package com.pacao.task_manager_api.task.dto;

import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.dto.UserSummaryResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.time.LocalDate;

@Builder

public record TaskResponse(
        Integer id,
        String title,
        String description,
        String statutus,
        LocalDate createdAt,
        UserSummaryResponse user) {

}

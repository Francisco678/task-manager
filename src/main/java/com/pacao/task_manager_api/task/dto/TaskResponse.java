package com.pacao.task_manager_api.task.dto;

import com.pacao.task_manager_api.users.entity.UserEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Builder

public record TaskResponse( Integer id,
        String title,
        String description,
        String statutus,
        String createdAt) {

}

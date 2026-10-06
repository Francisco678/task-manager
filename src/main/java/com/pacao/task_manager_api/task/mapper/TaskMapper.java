package com.pacao.task_manager_api.task.mapper;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.task.dto.TaskResponse;


public class TaskMapper {

    public static TaskResponse  taskEntityToResponse(TaskEntity entity){

        return TaskResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .statutus(entity.getStatutus())
                .createdAt(entity.getCreatedAt())
                .build();

    }
}

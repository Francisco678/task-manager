package com.pacao.task_manager_api.task.mapper;
import com.pacao.task_manager_api.task.dto.TaskRequest;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;
import com.pacao.task_manager_api.users.mapper.UserMapper;


public class TaskMapper {

    public static TaskResponse  taskEntityToResponse(TaskEntity entity){

        return TaskResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .statutus(entity.getStatutus())
                .createdAt(entity.getCreatedAt())
                .user(UserMapper.userEntityToSummaryResponse(entity.getUser()))
                .build();

    }


    public static TaskEntity taskRequestToEntity(TaskRequest request){
        return TaskEntity.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .statutus(request.statutus())
                .user(UserEntity.builder().id(request.idUser()).build())
                .build();

    }
}

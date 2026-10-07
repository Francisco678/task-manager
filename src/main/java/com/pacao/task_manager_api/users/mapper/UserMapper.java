package com.pacao.task_manager_api.users.mapper;

import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.task.mapper.TaskMapper;
import com.pacao.task_manager_api.users.dto.UserRequest;
import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.dto.UserSummaryResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;

import java.util.List;


public class UserMapper {

    public static UserResponse userEntityToResponse(UserEntity entity){

        List<TaskResponse> tasks = entity.getTasks()
                .stream()
                .map(TaskMapper::taskEntityToResponse)
                .toList();

        return UserResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .task(tasks)
                .build();
    }

    public static UserEntity userRequestToEntity(UserRequest request) {

        return UserEntity.builder()
                .id(request.id())
                .nombre(request.nombre())
                .email(request.email())
                .build();
    }

    public static UserSummaryResponse userEntityToSummaryResponse(UserEntity entity){

        return UserSummaryResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();

    }
}



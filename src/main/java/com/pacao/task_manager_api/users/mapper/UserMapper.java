package com.pacao.task_manager_api.users.mapper;

import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;

public class UserMapper {

    public static UserResponse userEntityToResponse(UserEntity entity){

        return UserResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail()).build();

    }

}

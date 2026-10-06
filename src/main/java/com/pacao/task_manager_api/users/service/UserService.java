package com.pacao.task_manager_api.users.service;

import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;
import com.pacao.task_manager_api.users.mapper.UserMapper;
import com.pacao.task_manager_api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository respository;

    public List<UserResponse> getUsers() {

        List<UserEntity> userEntities = respository.findAll();

        return userEntities.stream().map(UserMapper::userEntityToResponse).toList();
    }
}

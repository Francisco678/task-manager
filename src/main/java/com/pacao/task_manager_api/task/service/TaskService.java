package com.pacao.task_manager_api.task.service;

import com.pacao.task_manager_api.exception.TaskException;
import com.pacao.task_manager_api.task.dto.TaskRequest;
import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.task.mapper.TaskMapper;
import com.pacao.task_manager_api.task.repository.TaskRespository;
import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;
import com.pacao.task_manager_api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRespository repository;
    private final UserService userService;

    public List<TaskResponse> getTasks() {

        List<TaskEntity> taskEntities = repository.findAll();

        return taskEntities.stream().map(TaskMapper::taskEntityToResponse).toList();


    }

    public TaskResponse getTaskById(Integer idTask) {

         return repository.findById(idTask).map(TaskMapper::taskEntityToResponse).orElseThrow(()->new RuntimeException("Task no econtrada"));

    }

    public Integer createTask(TaskRequest request) {

        if(!userService.isUserInBd(request.idUser())){
            throw new TaskException("Id de usuario"+request.idUser()+" no valido para crear tarea");
        }

        TaskEntity entity = TaskMapper.taskRequestToEntity(request);

        TaskEntity createdTask= repository.save(entity);
        return createdTask.getId();

    }


}

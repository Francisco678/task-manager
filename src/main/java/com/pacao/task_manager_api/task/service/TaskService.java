package com.pacao.task_manager_api.task.service;

import com.pacao.task_manager_api.task.dto.TaskRequest;
import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.task.mapper.TaskMapper;
import com.pacao.task_manager_api.task.repository.TaskRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRespository repository;

    public List<TaskResponse> getTasks() {

        List<TaskEntity> taskEntities = repository.findAll();

        return taskEntities.stream().map(TaskMapper::taskEntityToResponse).toList();


    }

    public TaskResponse getTaskById(Integer idTask) {

        TaskEntity entity = repository.findById(idTask).orElseThrow(()->new RuntimeException("Task no econtrada"));

        return TaskMapper.taskEntityToResponse(entity);
    }

    public Integer createTask(TaskRequest request) {
        TaskEntity entity = TaskMapper.taskRequestToEntity(request);

        TaskEntity createdTask= repository.save(entity);
        return createdTask.getId();

    }
}

package com.pacao.task_manager_api.task.repository;

import com.pacao.task_manager_api.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRespository extends JpaRepository<TaskEntity,Integer> {
}

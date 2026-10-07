package com.pacao.task_manager_api.task.controller;

import com.pacao.task_manager_api.task.dto.TaskRequest;
import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.task.service.TaskService;
import com.pacao.task_manager_api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(){
        return ResponseEntity.ok(service.getTasks());
    }

    @GetMapping("/{idTask}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer idTask){
        return ResponseEntity.ok(service.getTaskById(idTask));
    }

    @PostMapping
    public ResponseEntity<Integer> crearTask(@RequestBody TaskRequest request){
        return ResponseEntity.ok(service.createTask(request));
    }
}

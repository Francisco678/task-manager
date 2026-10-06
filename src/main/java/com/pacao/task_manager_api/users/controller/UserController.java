package com.pacao.task_manager_api.users.controller;

import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(){

        return ResponseEntity.ok(service.getUsers());
    }

}


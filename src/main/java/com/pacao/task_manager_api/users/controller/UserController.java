package com.pacao.task_manager_api.users.controller;

import com.pacao.task_manager_api.users.dto.UserRequest;
import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer idUser){

        return ResponseEntity.ok(service.getUserById(idUser));

    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(service.createUser(request));
    }

}


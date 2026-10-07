package com.pacao.task_manager_api.advice;

import com.pacao.task_manager_api.error.ErrorResponse;
import com.pacao.task_manager_api.exception.TaskException;
import com.pacao.task_manager_api.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException exception){

        ErrorResponse error = new ErrorResponse("User error",exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<ErrorResponse> handle(TaskException exception){
        ErrorResponse error = new ErrorResponse("Task error",exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

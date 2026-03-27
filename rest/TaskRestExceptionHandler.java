package com.taskmanager.taskmanager.rest;

import com.taskmanager.taskmanager.entity.Tasks;
import com.taskmanager.taskmanager.exception.TaskErrorResponse;
import com.taskmanager.taskmanager.exception.taskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<TaskErrorResponse>  handleTaskNotFound(taskNotFoundException ex){
     TaskErrorResponse response = new TaskErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);


    }
}

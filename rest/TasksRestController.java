package com.taskmanager.taskmanager.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.taskmanager.DTO.TaskDTO;
import com.taskmanager.taskmanager.DTO.TaskResponseDTO;
import com.taskmanager.taskmanager.DTO.TaskStatusDTO;
import com.taskmanager.taskmanager.DTO.TaskUpdateDTO;
import com.taskmanager.taskmanager.entity.Tasks;
import com.taskmanager.taskmanager.entity.Users;
import com.taskmanager.taskmanager.events.TaskAssignedEvent;
import com.taskmanager.taskmanager.exception.taskNotFoundException;
import com.taskmanager.taskmanager.service.TaskService;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class TasksRestController {


    private final TaskService taskService;
    private UserService userService;
    private ObjectMapper objectMapper;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public TasksRestController(TaskService taskService, UserService userService, ObjectMapper objectMapper, ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.taskService = taskService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;

    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO>  getTasks() {
        return taskService.getTasks();

    }

    @GetMapping("/task/{taskId}")
    public TaskDTO getTaskById(@PathVariable Integer taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user =  userService.findUserByUsername(auth.getName());
      TaskDTO task = taskService.getTaskById(taskId);
      if(task == null) {
          throw new taskNotFoundException("Task not found");
      }
      return task;
    }

    @GetMapping("/user-tasks")
    public List<Tasks> getUserTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      Users user =  userService.findUserByUsername(auth.getName());
      if(user == null) {
//          throw new userNotFoundException()

      }

        System.out.println(user.getId());



        return taskService.getUserTasks(user.getId());
    }


    @PostMapping("/tasks")
    public void createTask(@RequestBody TaskDTO taskDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.findById(taskDTO.getUserId());
        if(user!=null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Tasks task = new Tasks();
            task.setTaskDescription(taskDTO.getTaskDescription());
            task.setUser(user);
            task.setDueDate(taskDTO.getDueDate());
            task.setTaskStatus(Tasks.TaskStatus.PENDING);
             //save the task
            taskService.saveTask(task);
            eventPublisher.publishEvent(
                    new TaskAssignedEvent(task.getUser().getUserName(), task.getTaskDescription())
            );
        }




    }

    @PatchMapping("/tasks/{taskId}")
    public void updateTask(@PathVariable Integer taskId, @RequestBody TaskUpdateDTO taskUpdateDTO) {

        TaskDTO existingTask =  taskService.getTaskById(taskId);
        System.out.println("Old: " + existingTask.getTaskDescription());
        System.out.println("New: " + taskUpdateDTO.getTaskDescription());
        System.out.println("New: " + taskUpdateDTO.getDueDate());


       if(existingTask == null) {
           throw new taskNotFoundException("Task not found");
       }

       taskService.updateTask(taskUpdateDTO, taskId);
    }

    @PatchMapping("/update-status/{taskId}")
    public void updateTaskStatus(@PathVariable Integer taskId, @RequestBody TaskStatusDTO taskStatusDTO) {
         taskService.updateStatus(taskId,taskStatusDTO);




    }


    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTaskById(taskId);
    }



}

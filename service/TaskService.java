package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.DTO.TaskDTO;
import com.taskmanager.taskmanager.DTO.TaskResponseDTO;
import com.taskmanager.taskmanager.DTO.TaskStatusDTO;
import com.taskmanager.taskmanager.DTO.TaskUpdateDTO;
import com.taskmanager.taskmanager.entity.Tasks;


import java.util.List;

public interface TaskService {
  List<TaskResponseDTO> getTasks();
  TaskDTO getTaskById(Integer id);
  void saveTask(Tasks task);
  void deleteTaskById(Integer id);
  void updateTask(TaskUpdateDTO task, Integer taskId);
  List<Tasks> getUserTasks(Integer userId);
  void updateStatus(Integer taskId,
                    TaskStatusDTO taskStatusDTO);

}

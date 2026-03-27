package com.taskmanager.taskmanager.DAO;

import com.taskmanager.taskmanager.DTO.TaskStatusDTO;
import com.taskmanager.taskmanager.DTO.TaskUpdateDTO;
import com.taskmanager.taskmanager.entity.Tasks;

import java.util.List;

public interface taskDao {
    List<Tasks> getTasks();
    Tasks getTaskById(Integer id);
    void saveTask(Tasks task);
    void deleteTaskById(Integer id);
    void updateTask(TaskUpdateDTO dto, Integer taskId);
    void updateTaskStatus(TaskStatusDTO taskStatusDTO, Integer taskId);
    List<Tasks> getUserTasks( Integer userId);
}

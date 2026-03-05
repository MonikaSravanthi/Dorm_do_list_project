package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.DAO.taskDao;
import com.taskmanager.taskmanager.DTO.TaskDTO;
import com.taskmanager.taskmanager.DTO.TaskResponseDTO;
import com.taskmanager.taskmanager.DTO.TaskStatusDTO;
import com.taskmanager.taskmanager.DTO.TaskUpdateDTO;
import com.taskmanager.taskmanager.entity.Tasks;
import com.taskmanager.taskmanager.exception.taskNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImple implements TaskService {

    private taskDao taskDao;
private FileUploadService fileUploadService;
    @Autowired
    public TaskServiceImple(taskDao taskDao,FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
        this.taskDao =taskDao;
    }

    @Override
    @Transactional
    public List<TaskResponseDTO> getTasks(){

      List<Tasks> tasks = taskDao.getTasks();

        return tasks.stream().map(task -> {
          TaskResponseDTO responseDTO = new TaskResponseDTO();
          responseDTO.setTaskId(task.getTaskId());
          responseDTO.setCompletedDate(task.getCompletedDate());
          responseDTO.setTaskDescription(task.getTaskDescription());
          responseDTO.setDueDate(task.getDueDate());

          responseDTO.setTaskStatus(task.getTaskStatus());

            String avatarKey = task.getUser().getAvatarUrl();
            if (avatarKey != null && !avatarKey.isEmpty()) {
                responseDTO.setProfilePicture(fileUploadService.generatePresignedUrl(avatarKey));
            } else {
                // if no image is found display default img
                responseDTO.setProfilePicture(fileUploadService.generatePresignedUrl("avatars/default-img.png"));
            }
          return responseDTO;
      }).toList();

    }

    @Override
    public TaskDTO getTaskById(Integer id){
        Tasks task = taskDao.getTaskById(id);
        TaskDTO dto = new TaskDTO();
        dto.setTaskDescription(task.getTaskDescription());
        dto.setDueDate(task.getDueDate());
        dto.setUserId(task.getUser().getId());
        return dto;

    }

    @Override
    public void saveTask(Tasks task){
        taskDao.saveTask(task);
    }
    @Override
    public void updateTask(TaskUpdateDTO dto, Integer taskId) {
        taskDao.updateTask(dto, taskId);
    }
    @Override
    public void updateStatus(Integer taskId, TaskStatusDTO taskStatusDTO ){
        taskDao.updateTaskStatus(taskStatusDTO, taskId);

    }

    @Override
    @Transactional
    public void deleteTaskById(Integer id){
        taskDao.deleteTaskById(id);
    }

    @Override
    public List<Tasks> getUserTasks(Integer userId){
        return taskDao.getUserTasks(userId);

    }


}

package com.taskmanager.taskmanager.DAO;

import com.taskmanager.taskmanager.DTO.TaskStatusDTO;
import com.taskmanager.taskmanager.DTO.TaskUpdateDTO;
import com.taskmanager.taskmanager.entity.Tasks;
import com.taskmanager.taskmanager.entity.Users;
import com.taskmanager.taskmanager.exception.taskNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;
import java.util.List;

@Repository
public class taskDaoImple implements taskDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public taskDaoImple(EntityManager em) {
        this.em = em;


    }
    @Override
    public List<Tasks> getTasks() {
           TypedQuery<Tasks> allTasks = em.createQuery("SELECT t FROM Tasks t  ", Tasks.class);
           return allTasks.getResultList();
    }

    @Override
    @Transactional
    public Tasks getTaskById(Integer id) {
        return em.find(Tasks.class, id);

    }
    @Override
    @Transactional
    public void saveTask(Tasks task) {
        em.persist(task);
    }

    @Override
    @Transactional
    public void updateTask(TaskUpdateDTO dto, Integer taskId) {
        Tasks existingTask = em.find(Tasks.class, taskId);

        if (existingTask == null) {
            throw new taskNotFoundException("Task not found");
        }

        if (dto.getTaskDescription() != null) {
            existingTask.setTaskDescription(dto.getTaskDescription());
        }

        if (dto.getDueDate() != null) {
            existingTask.setDueDate(dto.getDueDate());
        }

        if (dto.getUserId() != null) {
            Users user = em.find(Users.class, dto.getUserId());
            existingTask.setUser(user);
        }


    }
    @Override
    @Transactional
public void updateTaskStatus(TaskStatusDTO taskStatusDTO, Integer taskId){
        Tasks existingTask = em.find(Tasks.class, taskId);
        if (existingTask == null) {
            throw new taskNotFoundException("Task not found");
        }
         Tasks.TaskStatus status = taskStatusDTO.getTaskStatus();
        existingTask.setTaskStatus(status);
        System.out.println("status: " + status);

        if(status!=null){
            if (status == Tasks.TaskStatus.COMPLETED) {

                existingTask.setCompletedDate(LocalDate.now());
            } else {
                existingTask.setCompletedDate(null);
            }
        }



    }


    @Override
    public void deleteTaskById(Integer id) {
     Tasks taskToDelete = em.find(Tasks.class, id);
     em.remove(taskToDelete);
    }

    @Override
    public List<Tasks> getUserTasks(Integer userId){
        TypedQuery<Tasks> allTasks = em.createQuery("SELECT t FROM Tasks t WHERE t.user.id=:userId", Tasks.class);
        allTasks.setParameter("userId", userId);
        return allTasks.getResultList();

    }

}

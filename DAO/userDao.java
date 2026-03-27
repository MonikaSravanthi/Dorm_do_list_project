package com.taskmanager.taskmanager.DAO;


import com.taskmanager.taskmanager.entity.Users;


import java.util.List;

public interface userDao {
   List<Users> findALlUsers();
    Users findUserById(int id);
    Users findUserByName(String name);
    void saveUser(Users user);
  Users updateUser(Users user);
  void deleteUser(int id);
}

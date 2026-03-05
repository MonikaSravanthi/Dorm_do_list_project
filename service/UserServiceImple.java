package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.DAO.userDao;
import com.taskmanager.taskmanager.DTO.UserDTO;
import com.taskmanager.taskmanager.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Service
 public class UserServiceImple implements UserService {

    private final FileUploadService fileUploadService;
    private userDao userDao;
    private S3Client s3;

    @Value("${aws.bucket.name}")
    private String bucketName;


    @Autowired
    public UserServiceImple (S3Client s3Client, userDao userDao, FileUploadService fileUploadService){

        this.s3 = s3Client;
        this.userDao = userDao;
        this.fileUploadService = fileUploadService;
    }
   @Override
    public List<UserDTO> findALlUsers(){
      List<Users> users =  userDao.findALlUsers();
      return users.stream().map(user ->{
          UserDTO userDTO = new UserDTO();
          userDTO.setUserId(user.getId());
          userDTO.setFirstName(user.getFirstName());
          return userDTO;
      }).toList();

    }
    @Override
    public Users findById(int id) {
        return userDao.findUserById(id);
    }
    @Override
     public void save(Users user) {

         userDao.saveUser(user);
    }
    @Override
    public Users findUserByUsername(String username){
        return userDao.findUserByName(username);
    }


    @Override
    @Transactional
    public Users updateUser(Users user){
        return userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
       userDao.deleteUser(id);
    }

@Override
    @Transactional
public void saveUserImage(Integer userId,MultipartFile file) throws IOException {
        Users user = userDao.findUserById(userId);
        String key = fileUploadService.uploadFiletoS3(file,user.getId());
        user.setAvatarUrl(key);
        userDao.saveUser(user);
}



}

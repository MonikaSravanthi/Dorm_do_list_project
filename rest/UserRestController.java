package com.taskmanager.taskmanager.rest;

import com.taskmanager.taskmanager.DTO.LoginDTO;
import com.taskmanager.taskmanager.DTO.UserDTO;
import com.taskmanager.taskmanager.entity.Users;
import com.taskmanager.taskmanager.exception.UserNotFoundException;
import com.taskmanager.taskmanager.service.FileUploadService;
import com.taskmanager.taskmanager.service.JwtService;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRestController {
//private List<Users> users;
// private userDao userDao;
//@Autowired
//public UserRestController(userDao userDao) {
//    this.userDao = userDao;
//
//}

    private UserService userService;
    private FileUploadService fileUploadService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


     @Autowired
    public UserRestController(UserService userService, FileUploadService fileUploadService,
                              AuthenticationManager authenticationManager,JwtService jwtService
                              ) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

}

    @GetMapping("/users")
    public List<UserDTO> findAll() {
        return userService.findALlUsers();

    }
    @GetMapping("/users/{userId}")
    public Users findbyId(@PathVariable int userId){

        Users theUser =  userService.findById(userId);
        if(theUser == null){
            throw new UserNotFoundException("User with id " +" "+ userId + " "+" not found");
        }
     return theUser;
    }
    @GetMapping("/users/me")
    public Map<String, String>  currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.findUserByUsername(auth.getName());
        String key = user.getAvatarUrl();
        String url = null;
        if (key != null) {
                url = fileUploadService.generatePresignedUrl(key);

        }

        Map<String, String> response = new HashMap<>();
        response.put("avatarUrl", url);

        return response;

    }

    @PostMapping("/user")
    public void createUser(@RequestBody Users user) {

     userService.save(user);

    }
    @PutMapping("/user")
    public Users updateUser(@RequestBody Users user) {

      return userService.updateUser(user);
    }
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable int userId) {

    }
    @PostMapping("/user/file")
    public void uploadProfilePic(@RequestParam("image") MultipartFile file) throws IOException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       Users user = userService.findUserByUsername(auth.getName());
         userService.saveUserImage(user.getId(), file);

     }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>>  login(@RequestBody LoginDTO loginDTO) {
         System.out.println(loginDTO.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        String token = null;
        if(authentication.isAuthenticated()){
             System.out.println(authentication.getPrincipal());
//            authentication.getAuthorities();

              token =  jwtService.generateToken(authentication);
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }}



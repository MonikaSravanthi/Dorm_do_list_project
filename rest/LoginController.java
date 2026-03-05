package com.taskmanager.taskmanager.rest;

import com.taskmanager.taskmanager.DAO.userDao;
import com.taskmanager.taskmanager.entity.Authorities;
import com.taskmanager.taskmanager.entity.Users;

import com.taskmanager.taskmanager.repositories.AuthoritiesRepository;
import com.taskmanager.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class LoginController {


private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
  public LoginController(UserService userService, PasswordEncoder passwordEncoder, AuthoritiesRepository authoritiesRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesRepository = authoritiesRepository;


  }

    @PostMapping("/registration")
    public String signUp(@ModelAttribute Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        Authorities authorities = new Authorities();
        authorities.setAuthority("ROLE_USER");
        authorities.setUser(user);
        authoritiesRepository.save(authorities);
        return "redirect:/login.html";
    }}


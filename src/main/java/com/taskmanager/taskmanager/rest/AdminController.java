package com.taskmanager.taskmanager.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/data")
    public ResponseEntity<?> getAdminData() {
        // This will only be called if JwtAuthFilter authenticated the user
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Welcome, admin!");
        return ResponseEntity.ok(data);
    }
}
package com.taskmanager.taskmanager.events;

public record TaskAssignedEvent(String email, String taskTitle) {}

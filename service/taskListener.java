package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.events.TaskAssignedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class taskListener {

        private final MailServer mailService;

        @Autowired
        public taskListener(MailServer mailService) {
            this.mailService = mailService;
        }

        @EventListener
        public void handle(TaskAssignedEvent event) throws MessagingException {
            mailService.sendHtml(
                    event.email(),
                    "New Task Assigned",
                    "<p>" + event.taskTitle() + "</p>"
            );
        }
    }



package com.taskmanager.taskmanager.service;

import jakarta.mail.MessagingException;

public interface MailServer {
//    void sendPlainText(String to, String subject, String body);
    void sendHtml(String to, String subject, String htmlBody) throws MessagingException;
}

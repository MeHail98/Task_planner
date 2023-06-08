package com.example.task_planner.notification;

public interface SendMailService<E,M> {
    void sendMail(E mail,M message);
}
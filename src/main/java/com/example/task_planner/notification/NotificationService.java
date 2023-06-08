package com.example.task_planner.notification;

import com.example.task_planner.dto.TaskResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SendMailService<String,String> sendMailService;

    public NotificationService(SendMailService<String, String> sendMailService) {
        this.sendMailService = sendMailService;
    }


    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(Message message) throws JsonProcessingException {
        byte[] body= message.getBody();
        String jsonBody=new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskResponseDto taskResponseDto=objectMapper.readValue(jsonBody,TaskResponseDto.class);
        sendMailService.sendMail(taskResponseDto.username(),taskResponseDto.message());
    }
}
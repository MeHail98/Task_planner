package com.example.task_planner.service;

import com.example.task_planner.dto.TaskResponseDto;
import com.example.task_planner.entity.TaskEntity;
import com.example.task_planner.mapper.TaskResponseMapper;
import com.example.task_planner.repository.TaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskResponseService {

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final TaskRepository taskRepository;

    private final TaskResponseMapper taskResponseMapper;

    public TaskResponseService(RabbitTemplate rabbitTemplate, TaskRepository taskRepository, TaskResponseMapper taskResponseMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.taskRepository = taskRepository;
        this.taskResponseMapper = taskResponseMapper;
    }

    public List<TaskResponseDto> allTasks(){
        List<TaskEntity> all = (List<TaskEntity>) taskRepository.findAll();
        return all.stream().map(taskResponseMapper::mapFrom).toList();
    }


    public TaskResponseDto getTask(){
        return taskRepository.findById(11L)
                .map(taskResponseMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Scheduled(fixedDelay = 9000)
    public void produce(){
        LocalDateTime fromDate=LocalDateTime.now();
        LocalDateTime toDate=fromDate.plusSeconds(10);
        Set<TaskEntity> taskEntitiesByDateBetween =
                taskEntitiesByDateBetween(fromDate, toDate);

        ObjectMapper objectMapper=new ObjectMapper();



        if (!(taskEntitiesByDateBetween.isEmpty())){
            taskEntitiesByDateBetween
                    .stream()
                    .map(taskResponseMapper::mapFrom)
                    .forEach(taskResponseDto ->
                    {
                        try {
                            rabbitTemplate.convertAndSend(exchange,routingKey,objectMapper.writeValueAsString(taskResponseDto));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }


    }

    @Scheduled(fixedDelay = 10,timeUnit = TimeUnit.MINUTES)
    public void deleteOldTask(){
        LocalDateTime localDateTimeBefore = LocalDateTime.now().minusMinutes(5);
        deleteTask(taskRepository.findTaskEntitiesByDateBefore(localDateTimeBefore));
    }

    /**
     * {"customerCache"} - имя отдельной корзины для кеша, то что настроили в RedisCacheConfig.
     * Также такие методы должны быть public
     * Если объект удален из бд, то и из кеша удаляем в след методе.
     */
    @Cacheable(value = {"customerCache"})
     public Set<TaskEntity> taskEntitiesByDateBetween(LocalDateTime fromDate,LocalDateTime toDate){
        return taskRepository
                .findTaskEntitiesByDateBetween(fromDate, toDate);
    }


    @CacheEvict(value ={"customerCache"} )
    public void deleteTask(Set<TaskEntity> allIdOnDelete){
        taskRepository.deleteAll(allIdOnDelete);
    }
}

package com.example.task_planner.service;


import com.example.task_planner.dto.TaskRequestDto;
import com.example.task_planner.entity.TaskEntity;
import com.example.task_planner.entity.UserEntity;
import com.example.task_planner.mapper.Mapper;
import com.example.task_planner.repository.TaskRepository;
import com.example.task_planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskCreateService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Qualifier(value = "taskCreateMapper")
    private final Mapper<TaskRequestDto, TaskEntity> mapper;

    public TaskCreateService(TaskRepository taskRepository, UserRepository userRepository, Mapper<TaskRequestDto, TaskEntity> mapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * При добалвении нового объекта в БД, этот объект будет добавляться в кеш!!! не забыть todo
     */
    @CachePut(value = {"customerCache"})
    public boolean createNewTask(TaskRequestDto taskRequestDto ,@AuthenticationPrincipal UserDetails userDetails){
        TaskEntity taskEntity = mapper.mapFrom(taskRequestDto);
        String username = Optional.ofNullable(userDetails)
                .map(UserDetails::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("message"));

        UserEntity userEntity=userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("message"));
        taskEntity.setUserEntity(userEntity);

        taskRepository.save(taskEntity);

        return true;
    }
}

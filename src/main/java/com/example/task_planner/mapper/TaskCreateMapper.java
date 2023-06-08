package com.example.task_planner.mapper;

import com.example.task_planner.dto.TaskRequestDto;
import com.example.task_planner.entity.TaskEntity;
import com.example.task_planner.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateMapper implements Mapper<TaskRequestDto, TaskEntity> {
    @Override
    public TaskEntity mapFrom(TaskRequestDto object) {
        return TaskEntity.builder()
                .message(object.message())
                .date(object.dateTime())
                .build();
    }
}

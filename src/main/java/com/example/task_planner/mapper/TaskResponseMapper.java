package com.example.task_planner.mapper;

import com.example.task_planner.dto.TaskResponseDto;
import com.example.task_planner.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskResponseMapper implements Mapper<TaskEntity, TaskResponseDto> {
    @Override
    public TaskResponseDto mapFrom(TaskEntity object) {
        return new TaskResponseDto(object.getUserEntity().getUsername(),
                object.getMessage());
    }
}

package com.example.task_planner.repository;


import com.example.task_planner.entity.TaskEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Set;

public interface TaskRepository extends CrudRepository<TaskEntity,Long> {
    @EntityGraph(value = "WithUserEntity")
    Set<TaskEntity> findTaskEntitiesByDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
    Set<TaskEntity> findTaskEntitiesByDateBefore(LocalDateTime beforeDate);
}

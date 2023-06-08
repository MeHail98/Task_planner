package com.example.task_planner.dto;

import java.time.LocalDateTime;

public record TaskRequestDto (String message, LocalDateTime dateTime){
}

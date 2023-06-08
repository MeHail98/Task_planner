package com.example.task_planner.controller;


import com.example.task_planner.dto.TaskRequestDto;
import com.example.task_planner.dto.TaskResponseDto;
import com.example.task_planner.service.TaskCreateService;
import com.example.task_planner.service.TaskResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CreateTaskController {
    private final TaskCreateService taskCreateService;
    private final TaskResponseService taskResponseService;

    public CreateTaskController(TaskCreateService taskCreateService, TaskResponseService taskResponseService) {
        this.taskCreateService = taskCreateService;
        this.taskResponseService = taskResponseService;
    }

    @PostMapping("/createNewTask")
    public boolean createNewTask(@RequestBody TaskRequestDto taskRequestDto,@AuthenticationPrincipal UserDetails userDetails){
        taskCreateService.createNewTask(taskRequestDto,userDetails);
        return true;
    }
    @GetMapping("/getAllTasks")
    public List<TaskResponseDto> getAllTasks(){
//        List<ResponseEntity<TaskResponseDto>> responseEntities=new ArrayList<>();
//        List<TaskResponseDto> taskResponseDto = taskResponseService.allTasks();
//        taskResponseDto.forEach(taskResponseDto1 -> responseEntities
//                .add(new ResponseEntity<>(taskResponseDto1, HttpStatus.OK)));
        return taskResponseService.allTasks();
    }

    @GetMapping("/getTask")
    public TaskResponseDto getTask(){
        return taskResponseService.getTask();
    }

}

package com.example.task_planner.controller;


import com.example.task_planner.dto.UserRegistrationDto;
import com.example.task_planner.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public boolean registrationNewUser(@RequestBody UserRegistrationDto userRegistrationDto){
        userService.createUser(userRegistrationDto);
        return true;
    }
}

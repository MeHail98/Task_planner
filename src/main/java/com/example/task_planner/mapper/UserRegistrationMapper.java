package com.example.task_planner.mapper;

import com.example.task_planner.dto.UserRegistrationDto;
import com.example.task_planner.entity.Role;
import com.example.task_planner.entity.UserEntity;
import org.springframework.stereotype.Component;


@Component
public class UserRegistrationMapper implements Mapper<UserRegistrationDto, UserEntity> {
    @Override
    public UserEntity mapFrom(UserRegistrationDto object) {
        return UserEntity.builder()
                .username(object.username())
                .password(object.password())
                .role(Role.USER)
                .build();
    }
}

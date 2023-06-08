package com.example.task_planner.service;


import com.example.task_planner.dto.UserRegistrationDto;
import com.example.task_planner.entity.UserEntity;
import com.example.task_planner.mapper.Mapper;
import com.example.task_planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier(value = "userRegistrationMapper")
    private final Mapper<UserRegistrationDto, UserEntity> mapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Mapper<UserRegistrationDto, UserEntity> mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public void createUser(UserRegistrationDto userRegistrationDto){
        UserEntity userEntity=mapper.mapFrom(userRegistrationDto);
        Optional.ofNullable(userRegistrationDto.password())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(userEntity::setPassword);
        userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(users -> new org.springframework.security.core.userdetails.User(users.getUsername()
                        ,users.getPassword()
                        , Collections.singleton(users.getRole())))
                .orElseThrow(()->new UsernameNotFoundException("Failed to retrieve user: "+username));
    }
}

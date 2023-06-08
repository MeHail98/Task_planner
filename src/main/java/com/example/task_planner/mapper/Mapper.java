package com.example.task_planner.mapper;

public interface Mapper <F,T>{
    T mapFrom(F object);
}

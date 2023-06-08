--liquibase formatted sql

--changeset mikhail:1
create table if not exists public.users
(
    id       bigserial
        primary key,
    username varchar(128) not null
        unique,
    password varchar(128) not null,
    role     varchar(128)
);

--changeset mikhail:2
create table if not exists public.task
(
    id      bigserial
        primary key,
    user_id bigint       not null
        references users,
    message varchar(256) not null,
    date    timestamp    not null
);

--changeset mikhail:3
create index date_idx
    on task (date);
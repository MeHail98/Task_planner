--liquibase formatted sql

--changeset mikhail:1
INSERT INTO users (id, username, password, role) VALUES (1, 'Mihail', '{bcrypt}$2a$10$ckTTxKgniip0VI/n4QubpOldPfYMnFE3fo9HYOESfZ0oiPW2fPOMq', 'USER');
INSERT INTO users (id, username, password, role) VALUES (2, 'mehail.conversation@gmail.com', '{bcrypt}$2a$10$tmB8SHWTuJpTIJG6pVKeMeuICDONYQZ.3T2nDp.QzC1/mqKpvWqZS', 'USER');

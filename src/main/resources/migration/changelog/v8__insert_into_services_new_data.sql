--liquibase formatted sql
--changeset antoxakon:2  -- Формат: author:id
--comment: add new subscriptions kinopoisk

insert into user_subscriptions.services (id, name) VALUES (5,'kinopoisk');
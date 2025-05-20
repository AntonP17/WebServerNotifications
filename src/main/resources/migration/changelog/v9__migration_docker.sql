--liquibase formatted sql
--changeset antoxakon:3  -- Формат: author:id
--comment: add subscriptions in docker

insert into public.services (id, name) VALUES (1,'kinopoisk');
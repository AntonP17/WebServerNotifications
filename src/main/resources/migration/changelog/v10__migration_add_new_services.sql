--liquibase formatted sql
--changeset antoxakon:4  -- Формат: author:id
--comment: add new 2 services!
insert into services (id, name) VALUES (1,'kinopoisk'),
                                         (2,'netflix'),
                                       (3,'vk_music');
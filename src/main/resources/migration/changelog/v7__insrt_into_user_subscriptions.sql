--liquibase formatted sql
--changeset antoxakon:1  -- Формат: author:id
--comment: add new subscriptions

insert into user_subscriptions.services (id, name) VALUES (4,'zetflix');
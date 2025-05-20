--liquibase formatted sql
--changeset antoxakon:1  -- Формат: author:id
--comment: Добавление тестовых данных в таблицу test_by_schema

INSERT INTO test_schema.test_by_schema_v2 (id, name) VALUES (6, 'Anton');
INSERT INTO test_schema.test_by_schema_v2 (id, name) VALUES (7, 'Maria');
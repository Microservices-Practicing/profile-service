--liquibase formatted sql
--changeset HOAI:PROFILE-SERVICE-1
CREATE TABLE profile (
  "id" bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "user_id" varchar(36),
  "first_name" varchar,
  "middle_name" varchar,
  "last_name" varchar,
  "profile_status" varchar,
  "created_by" varchar,
  "created_date" timestamp,
  "updated_date" timestamp
);

create extension if not exists "uuid-ossp";

create table if not exists "museum"
(
    id          UUID unique        not null default uuid_generate_v1(),
    title       varchar(255)  unique not null,
    description varchar(1000),
    city        varchar(255),
    photo       bytea,
    country_id  UUID     not null,
    primary key (id)
);

alter table "museum"
    owner to postgres;

delete from "museum";
create extension if not exists "uuid-ossp";

create table if not exists "user"
(
    id                      UUID unique        not null default uuid_generate_v1(),
    username  varchar(50)  unique not null,
    firstname varchar(255),
    lastname  varchar(255),
    avatar    bytea,
    primary key (id)
);

alter table "user"
    owner to postgres;
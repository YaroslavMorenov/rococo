create extension if not exists "uuid-ossp";

create table if not exists "painting"
(
    id          UUID unique        not null default uuid_generate_v1(),
    title       varchar(255)    not null,
    description varchar(1000),
    artist_id   UUID     not null,
    museum_id   UUID,
    content     bytea,
    primary key (id)
);

alter table "painting"
    owner to postgres;

delete from "painting";
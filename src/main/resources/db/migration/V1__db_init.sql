create table if not exists user_info
(
    id                    bigserial,
    login                 varchar(36) unique not null,
    first_name            varchar(36) not null,
    second_name           varchar(36) not null,
    birth_date            date,
    sex                   varchar(36),
    biography             varchar(255),
    primary key (id)
);
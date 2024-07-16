create table if not exists user_info
(
    id                    bigserial,
    username              varchar(36) unique not null,
    first_name            varchar(36),
    second_name           varchar(36),
    birth_date            date,
    sex                   varchar(36),
    biography             varchar(255),
    city                  varchar(36),
    primary key (id)
);

create table if not exists account
(
    username           varchar(36),
    password           varchar(255) unique not null,
    enabled            boolean not null default true,
    foreign key(username) references user_info(username)
);
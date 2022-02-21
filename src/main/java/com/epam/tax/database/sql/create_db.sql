DROP database IF EXISTS schema_tax;

CREATE database schema_tax;

USE schema_tax;

create table roles
(
    id        bigint      not null
        primary key,
    role_name varchar(50) not null,
    constraint roles_id_uindex
        unique (id)
);

INSERT INTO roles
VALUES (1, 'inspector');
INSERT INTO roles
VALUES (2, 'client');

CREATE TABLE statuses
(
    id          integer     not null,
    status_name varchar(20) not null,
    primary key (id),
    unique (status_name)
);
INSERT INTO statuses
VALUES (0, 'submitted');
INSERT INTO statuses
VALUES (1, 'confirmed');
INSERT INTO statuses
VALUES (2, 'not confirmed');


create table users
(
    id       bigint auto_increment
        primary key,
    name     varchar(20)  not null,
    role_id  bigint DEFAULT 2,
    username varchar(256) not null,
    password varchar(256) not null,
    surname  varchar(46)  not null,
    constraint users_id_uindex
        unique (id),
    unique (username),
    constraint role_id
        foreign key (role_id) references roles (id)
);

create table reports
(
    id           bigint auto_increment
        primary key,
    date         date         not null,
    status_id    integer      not null,
    type         varchar(50)  not null,
    user_id      bigint       not null,
    comment      varchar(256) null,
    inspector_id bigint       null,
    constraint reports_id_uindex
        unique (id),
    constraint user_id
        foreign key (user_id) references users (id) on delete cascade,
    constraint status_id
        foreign key (status_id) references statuses (id),
    constraint inspector_id
        foreign key (inspector_id) references users (id)
);
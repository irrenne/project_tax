DROP database IF EXISTS schema_tax;
CREATE database schema_tax character set utf8;
USE schema_tax;
create table language
(
    id        bigint auto_increment
        primary key,
    lang_name varchar(50) not null,
    constraint language_id_uindex
        unique (id),
    constraint language_lang_name_uindex
        unique (lang_name)
);

create table roles
(
    id        bigint      not null
        primary key,
    role_name varchar(50) not null,
    constraint roles_id_uindex
        unique (id)
)
    charset = utf8;
INSERT INTO roles
VALUES (1, 'inspector');
INSERT INTO roles
VALUES (2, 'client');

create table statuses
(
    id          int         not null
        primary key,
    status_name varchar(20) not null,
    constraint status_name
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
    name     varchar(20)      not null,
    role_id  bigint default 2 null,
    username varchar(256)     not null,
    password varchar(256)     not null,
    surname  varchar(46)      not null,
    constraint username
        unique (username),
    constraint users_id_uindex
        unique (id),
    constraint role_id
        foreign key (role_id) references roles (id)
);
INSERT INTO users
VALUES (1, 'Андрій', 1, 'інспектор', '123', 'Бабай');
create table reports
(
    id            bigint auto_increment
        primary key,
    date          date         not null,
    status_id     int          not null,
    type          varchar(50)  not null,
    user_id       bigint       not null,
    comment       varchar(256) null,
    document_blob mediumblob   null,
    inspector_id  bigint       null,
    file_name     varchar(50)  not null,
    constraint reports_id_uindex
        unique (id),
    constraint inspector_id
        foreign key (inspector_id) references users (id),
    constraint status_id
        foreign key (status_id) references statuses (id),
    constraint user_id
        foreign key (user_id) references users (id)
            on delete cascade
);



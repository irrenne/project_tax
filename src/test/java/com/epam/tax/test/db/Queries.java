package com.epam.tax.test.db;

public class Queries {
    static final String DROP_DB_TEST= "DROP database IF EXISTS schema_tax_test";
    static final String CREATE_DB_TEST = "CREATE database schema_tax_test";
    static final String CREATE_ROLE_TABLE =
            "create table roles\n" +
                    "(\n" +
                    "    id   bigint auto_increment\n" +
                    "        primary key,\n" +
                    "    name varchar(50) not null,\n" +
                    "    constraint roles_id_uindex\n" +
                    "        unique (id)\n" +
                    ")";

    static final String CREATE_USERS_TABLE =
            "create table users\n" +
                    "(\n" +
                    "    id       bigint auto_increment\n" +
                    "        primary key,\n" +
                    "    name     varchar(20)  not null,\n" +
                    "    role_id  bigint       null,\n" +
                    "    username varchar(256) not null,\n" +
                    "    password varchar(256) not null,\n" +
                    "    surname  varchar(46)  not null,\n" +
                    "    constraint users_id_uindex\n" +
                    "        unique (id),\n" +
                    "    constraint role_id\n" +
                    "        foreign key (role_id) references roles (id)\n" +
                    ")";

    static final String CREATE_REPORT_TABLE =
            " create table reports\n" +
                    "(\n" +
                    "    id      bigint auto_increment\n" +
                    "        primary key,\n" +
                    "    date    date                                             not null,\n" +
                    "    status  enum ('SUBMITTED', 'CONFIRMED', 'NOT_CONFIRMED') not null,\n" +
                    "    type    varchar(50)                                      not null,\n" +
                    "    user_id bigint                                           not null,\n" +
                    "    constraint reports_id_uindex\n" +
                    "        unique (id),\n" +
                    "    constraint user_id\n" +
                    "        foreign key (user_id) references users (id)\n" +
                    ")";


    static final String DROP_ROLE_TABLE = "DROP TABLE roles";

    static final String DROP_USERS_TABLE = "DROP TABLE users";

    static final String DROP_REPORT_TABLE = "DROP TABLE reports";

}

create table roles
(
    id              bigint generated by default as identity not null,
    role_type       varchar(255)    not null,

    constraint pk_roles primary key (id)
);

create table users
(
    id              bigint generated by default as identity not null,
    email           varchar(255)    not null unique,

    constraint pk_users primary key (id)
);

create table users_roles
(
    user_id     bigint  not null,
    role_id     bigint  not null,

    primary key (user_id, role_id),
    constraint FK_users_roles_user_id foreign key (user_id) references users (id),
    constraint FK_users_roles_role_id foreign key (role_id) references roles (id)
);
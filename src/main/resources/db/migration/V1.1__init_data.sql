begin;

insert into users (id, email) values
    (1, 'xxx@gmail.com'),
    (2, 'hodspodska@gmail.com');
SELECT setval('users_id_seq', 2);

insert into roles (id, role_type) values
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN'),
    (3, 'ROLE_PUB_OWNER');
SELECT setval('roles_id_seq', 3);

insert into users_roles (user_id, role_id) values
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 3);

commit;
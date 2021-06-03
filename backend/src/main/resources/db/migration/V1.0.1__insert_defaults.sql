insert into authority (id, name)
values (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

insert into users (id, name, username, password, enabled)
values (1, 'Admin', 'admin',
        '$2a$10$W5G9XQfDQ6fcokLMXNv.2ebewbUXCYoOYOW9iSeF9GHd6EFRB36eG',
        true);

insert into user_authority (user_id, authority_id)
values (1, 1),
       (1, 2)

create table category
(
    id   serial,
    name varchar(100) unique,
    primary key (id)
);

create table product
(
    id          serial,
    name        varchar(100) unique,
    category_id bigint unsigned,
    description varchar(255),
    price       decimal(15, 4),
    client_id   bigint unsigned,
    foreign key (category_id) references category (id),
    foreign key (client_id) references users (id),
    primary key (id)
);

create table orders
(
    id          serial,
    client_id   bigint unsigned,
    total_price decimal(15, 4),
    paied       boolean,
    foreign key (client_id) references users (id),
    primary key (id)
);

create table ordered_products (
    id serial,
    name varchar(100),
    description varchar(255),
    unit_price decimal(15, 4),
    order_id bigint unsigned,
    foreign key (order_id) references orders(id),
    primary key (id)
);

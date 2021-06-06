create table category
(
    id   serial,
    name varchar(100) unique,
    primary key (id)
);

create table images
(
    id       serial,
    name     varchar(255) unique,
    location varchar(500) unique,
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
    image_id    bigint unsigned not null,
    foreign key (category_id) references category (id),
    foreign key (client_id) references users (id),
    foreign key (image_id) references images (id),
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

create table ordered_products
(
    id          serial,
    name        varchar(100),
    description varchar(255),
    unit_price  decimal(15, 4),
    quantity    int,
    order_id    bigint unsigned,
    foreign key (order_id) references orders (id),
    primary key (id)
);

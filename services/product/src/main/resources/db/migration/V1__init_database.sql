create table if not exists category (
    id int not null,
    name varchar(255) not null,
    description varchar(255),
    primary key (id)
);

create table if not exists product (
    id int not null,
    name varchar(255) not null,
    description varchar(255),
    quantity double precision not null,
    price numeric(38, 2) not null,
    category_id int constraint fk_product_category_id references category (id),
    primary key (id)
);

create sequence if not exists category_seq increment by 50;
create sequence if not exists product_seq increment by 50;
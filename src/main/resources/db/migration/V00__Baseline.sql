
    drop table customer if exists;

    drop sequence if exists customer_seq;

    create table customer (
        id bigint not null,
        first_name varchar(100),
        last_name varchar(100) not null,
        primary key (id)
    );

    create sequence customer_seq start with 1 increment by 1;

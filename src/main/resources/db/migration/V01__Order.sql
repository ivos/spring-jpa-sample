alter table customer add column version bigint not null;

    create table order_ (
        id bigint not null,
        effective_date date not null,
        order_number varchar(10) not null,
        version bigint not null,
        customer bigint not null,
        primary key (id)
    );

    create table order_item (
        id bigint not null,
        product varchar(100) not null,
        quantity integer not null,
        order_ bigint not null,
        primary key (id)
    );

    alter table order_ 
        add constraint uc_order__orderNumber  unique (order_number);

    alter table order_ 
        add constraint fk_order__customer 
        foreign key (customer) 
        references customer;

    alter table order_item 
        add constraint fk_order_item__order 
        foreign key (order_) 
        references order_;

    create sequence order_item_seq start with 1 increment by 1;

    create sequence order_seq start with 1 increment by 1;

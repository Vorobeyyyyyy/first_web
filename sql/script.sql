create table if not exists users
(
    login             varchar(16)  not null,
    password          char(20)     not null,
    first_name        char(20)     not null,
    second_name       char(20)     not null,
    email             char(254)    null,
    user_level        varchar(30)  null,
    avatar_image_path varchar(255) null,
    constraint users_login_uindex
        unique (login)
);

create table if not exists publications
(
    publicationId    bigint auto_increment,
    title            char(150)    null,
    preview_img_path varchar(255) null,
    summary          mediumtext   null,
    content          longtext     null,
    date             bigint       null,
    publisher        varchar(16)  null,
    constraint publications_publicationId_uindex
        unique (publicationId),
    constraint publications_users_login_fk
        foreign key (publisher) references users (login)
);

alter table publications
    add primary key (publicationId);

create table if not exists commends
(
    commendId     bigint auto_increment,
    body          tinytext    null,
    publisherId   varchar(16) null,
    publicationId bigint      null,
    date          bigint      null,
    constraint commends_commendId_uindex
        unique (commendId),
    constraint commends_publications_publicationId_fk
        foreign key (publicationId) references publications (publicationId)
            on delete cascade,
    constraint commends_users_login_fk
        foreign key (publisherId) references users (login)
            on delete cascade
);

alter table commends
    add primary key (commendId);

create table if not exists likes
(
    userId    varchar(16) null,
    commendId bigint      null,
    constraint likes_pk
        unique (userId, commendId),
    constraint likes_commends_commendId_fk
        foreign key (commendId) references commends (commendId)
            on delete cascade,
    constraint likes_users_login_fk
        foreign key (userId) references users (login)
            on delete cascade
);



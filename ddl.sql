create table favorite_ski_resort
(
    id            CHAR(16) FOR BIT DATA not null,
    ski_resort_id CHAR(16) FOR BIT DATA not null,
    user_id       CHAR(16) FOR BIT DATA not null,
    primary key (id)
);
create table friendship
(
    friendship_id CHAR(16) FOR BIT DATA not null,
    confirmed     boolean               not null,
    confirmer_id  CHAR(16) FOR BIT DATA not null,
    requester_id  CHAR(16) FOR BIT DATA not null,
    primary key (friendship_id)
);
create table message
(
    message_id  CHAR(16) FOR BIT DATA not null,
    content     varchar(255)          not null,
    timestamp   timestamp             not null,
    receiver_id CHAR(16) FOR BIT DATA not null,
    sender_id   CHAR(16) FOR BIT DATA not null,
    primary key (message_id)
);
create table post
(
    post_id    CHAR(16) FOR BIT DATA not null,
    content    varchar(255)          not null,
    image_path varchar(255),
    timestamp  timestamp,
    user_id    CHAR(16) FOR BIT DATA not null,
    primary key (post_id)
);
create table ski_resort
(
    ski_resort_id CHAR(16) FOR BIT DATA not null,
    latitude      double                not null,
    longitude     double                not null,
    name          varchar(100)          not null,
    primary key (ski_resort_id)
);
create table trip
(
    trip_id    CHAR(16) FOR BIT DATA not null,
    distance   float                 not null,
    end_time   timestamp             not null,
    start_time timestamp             not null,
    user_id    CHAR(16) FOR BIT DATA not null,
    primary key (trip_id)
);
create table user_profile
(
    user_id    CHAR(16) FOR BIT DATA not null,
    bio        varchar(255),
    image_path varchar(255),
    name       varchar(255)          not null,
    oauth_key  varchar(255)          not null,
    primary key (user_id)
);
create unique index UKdq442pqtqnv46d4p3mpn8ivpc on favorite_ski_resort (user_id, ski_resort_id);
create index IDXxnc9y4tn2lhwwft8k441erlx on message (timestamp);
create index IDX7ji8inf1e51c49bmnqs2ckgji on post (timestamp);
create index IDXa9jk6nf92g2sxewvo5cfpwh06 on ski_resort (name);
create index IDX6b1jc6mqt3944x5rjvkuotq9 on ski_resort (latitude);
create index IDXtdd1ufqv5x0v3icon1v9e384g on ski_resort (longitude);
alter table ski_resort
    add constraint UK_a9jk6nf92g2sxewvo5cfpwh06 unique (name);
create index IDXp01rshi7enlmlnqwq0f8l7pna on trip (start_time);
create index IDX6joybjj4bpihh8ek99xdebyvw on trip (end_time);
create index IDX1ev94k2f518ffkfyln4k5w12y on trip (distance);
alter table user_profile
    add constraint UK_6f815wi5o4jq8p1q1w63o4mhd unique (oauth_key);
alter table favorite_ski_resort
    add constraint FK8uisnwim3hg4i3auxgtkim6g2 foreign key (ski_resort_id) references ski_resort;
alter table favorite_ski_resort
    add constraint FK4iwev6o7drpai7swm29dwkvlt foreign key (user_id) references user_profile;
alter table friendship
    add constraint FKfafn8f3ahmdtd1fdldlpuckk9 foreign key (confirmer_id) references user_profile;
alter table friendship
    add constraint FK64dvlo0k3rawusu1s8yvu86x9 foreign key (requester_id) references user_profile;
alter table message
    add constraint FKl9pm22be7914acf8pjgc6125p foreign key (receiver_id) references user_profile;
alter table message
    add constraint FKkqbyufjgk44k5472f9i9na5d1 foreign key (sender_id) references user_profile;
alter table post
    add constraint FK76inb3gj7lu8odgxhx6teqad3 foreign key (user_id) references user_profile;
alter table trip
    add constraint FKi4kctnejaom994fs9boqkrrgx foreign key (user_id) references user_profile;
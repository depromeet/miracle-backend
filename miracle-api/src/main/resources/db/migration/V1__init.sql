CREATE TABLE IF NOT EXISTS SPRING_SESSION (
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);

CREATE INDEX IF NOT EXISTS SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);

CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES (
    SESSION_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);

create or replace table alarm_schedule
(
    id bigint auto_increment
        primary key,
    created_date_time datetime(6) null,
    last_modified_date_time datetime(6) null,
    description varchar(255) null,
    member_id bigint null,
    type varchar(255) null
);

create or replace table alarm
(
    id bigint auto_increment
    primary key,
    created_date_time datetime(6) null,
    last_modified_date_time datetime(6) null,
    day_of_the_week varchar(255) null,
    reminder_time time null,
    alarm_schedule_id bigint null,
    constraint FK47fiec2779fvf1t15d32fqyu8
    foreign key (alarm_schedule_id) references alarm_schedule (id)
);

create or replace table member
(
    id bigint auto_increment
        primary key,
    created_date_time datetime(6) null,
    last_modified_date_time datetime(6) null,
    email varchar(255) not null,
    name varchar(255) null,
    profile_icon varchar(255) null,
    provider varchar(255) null,
    type varchar(255) null
);

create or replace table member_goal
(
    id bigint auto_increment
        primary key,
    created_date_time datetime(6) null,
    last_modified_date_time datetime(6) null,
    category varchar(255) null,
    member_id bigint null,
    constraint FKc1kd1y6taexe118k98eptqa0b
        foreign key (member_id) references member (id)
);

create or replace table schedule
(
    id bigint auto_increment
        primary key,
    created_date_time datetime(6) null,
    last_modified_date_time datetime(6) null,
    category varchar(255) null,
    day int not null,
    day_of_week varchar(255) null,
    description varchar(255) null,
    end_time time null,
    loop_type varchar(255) null,
    member_id bigint not null,
    month int not null,
    start_time time null,
    year int not null
);

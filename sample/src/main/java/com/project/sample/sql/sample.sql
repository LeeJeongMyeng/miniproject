# schema 사용 선언
use sample;
# 한글설정
alter database sample default character set utf8 collate  utf8_general_ci;
alter table user3 default charset = utf8;
ALTER TABLE user3 CONVERT TO CHARSET UTF8;


create table user3(
    no int not null auto_increment primary key ,
    name varchar(100) not null ,
    email varchar(200) not null ,
    pwd varchar(100) not null ,
    gender boolean not null default 1,
    del boolean not null default 0,
    regData datetime not null default now()
);

insert into user3 (name,email,pwd,gender) value ('사용자0','test@test.com','1111',0);


select * from user3;
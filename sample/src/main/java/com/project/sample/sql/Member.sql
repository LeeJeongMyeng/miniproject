CREATE TABLE ctg_Member(
                         userno         varchar(10) PRIMARY KEY,
                         email          varchar(100),
                         password       varchar(20),
                         name           varchar(4),
                         personalNumber varchar(14),
                         phoneNumber    varchar(11),
                         address        varchar(200),
                         bnCheck        boolean
);

create table ctg_user_bs(
    userno   varchar(10) primary key,
    bnNumber varchar(100)
);

drop table ctg_user;

select * from ctg_member where email = #{email,jdbcType=VARVHAR}




CREATE TABLE ctg_Member(
                         userno         int        PRIMARY KEY, #유저번호
                         email          varchar(100),            #이메일
                         password       varchar(100),             #비밀번호
                         name           varchar(100),              #이름
                         personalNumber varchar(100),             #주민
                         phoneNumber    varchar(100),             #폰번호
                         postcode       int,                     #우편번호
                         address        varchar(100),            #주소
                         extraAddress   varchar(100),            #부가주소
                         detailAddress  varchar(100),            #상세주소
                         bnCheck        boolean,                 #사업자여부
                         state          boolean,                 #탈퇴여부
                         joinDate       date,                    #가입날짜
                         delDate        date                     #탈퇴날짜
);

create table ctg_user_bs(
    userno   varchar(13) primary key,
    bnNumber varchar(100)
);

drop TABLE ctg_Member;

create table sequences
(
    name varchar(32),
    currval bigint unsigned
) engine = InnoDB;

create procedure `create_sequence` (In seq_name text)
    modifies sql data
    deterministic
begin
    delete from sequences where name = seq_name;
    insert into sequences values(seq_name, 0);
end;

create function `nextval` (seq_name varchar(32))
    returns bigint unsigned
    modifies sql data
    deterministic
begin
    declare ret bigint unsigned;
    update sequences set currval = currval + 1 where name = seq_name;
    select currval into ret from sequences where name = seq_name limit 1;
    return ret;
end;


call create_sequence('seq_member');


SELECT * FROM CTG_MEMBER;

COMMIT;
-- mybatis 회원가입 양식
# INSERT INTO CTG_MEMBER VALUES (userno,email,password,name,PERSONALNUMBER,PHONENUMBER,POSTCODE,ADDRESS,EXTRAADDRESS,DETAILADDRESS,bnCheck,STATE,JOINDATE,DELDATE);

INSERT INTO CTG_MEMBER VALUES (CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),nextval('seq_member')),
                               'email',
                               'password',
                               'name',
                               'PERSONALNUMBER',
                               'PHONENUMBER',
                               1234,
                               'ADDRESS',
                               'EXTRAADDRESS',
                               'DETAILADDRESS',
                               FALSE,
                               TRUE,
                               SYSDATE(),
                               NULL
    );

SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),nextval('seq_member')) FROM DUAL;

DELETE FROM CTG_MEMBER WHERE USERNO = 2023090520;

COMMIT ;

-- 회원 이름/주민 중복여부
SELECT COUNT(*) FROM CTG_MEMBER
where NAME = #{name,jdbcType=VARCHAR}
and PERSONALNUMBER = #{personalNumber},jdbcType=VARCHAR};

-- 회원 이메일 중복여부
SELECT COUNT(*) FROM CTG_MEMBER
where EMAIL = '';


SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),'-','B-',0) FROM DUAL;

SELECT USERNO,NAME,EMAIL,PASSWORD,BNCHECK FROM CTG_MEMBER
WHERE STATE = 1
  AND EMAIL = 'z@nate.com';


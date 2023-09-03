CREATE TABLE ctg_Member(
                         userno         varchar(10) PRIMARY KEY, #유저번호
                         email          varchar(100),            #이메일
                         password       varchar(20),             #비밀번호
                         name           varchar(4),              #이름
                         personalNumber varchar(14),             #주민
                         phoneNumber    varchar(11),             #폰번호
                         address        varchar(200),            #주소
                         bnCheck        boolean,                 #사업자여부
                         state          boolean,                 #탈퇴여부
                         joinDate       date,                    #가입날짜
                         delDate        date                     #탈퇴날짜
);

create table ctg_user_bs(
    userno   varchar(10) primary key,
    bnNumber varchar(100)
);

drop TABLE CTG_MEMBER;

SELECT * FROM CTG_MEMBER;

INSERT INTO CTG_MEMBER VALUES ('9','aoddl56@nate.com','1111','이정명','950828-1111111','01052930247','창원',false,true,sysdate(),null);

DELETE FROM CTG_MEMBER where userno = '9';

COMMIT ;

-- 회원 이름/주민 중복여부
SELECT COUNT(*) FROM CTG_MEMBER
where NAME = #{name,jdbcType=VARCHAR}
and PERSONALNUMBER = #{personalNumber,jdbcType=VARCHAR};

-- 회원 이메일 중복여부
SELECT COUNT(*) FROM CTG_MEMBER
where EMAIL = '';



INSERT INTO CTG_MEMBER VALUES (
    '9',
    'aoddl56@nate.com',
    '1111',
    '이정명',
    '950828-1111111',
    '01052930247',
    '창원',
    false,true,sysdate(),null
    );

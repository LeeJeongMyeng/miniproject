CREATE TABLE ctg_Member(
                         user_id          int       PRIMARY KEY,    #유저번호
                         email            varchar(100),             #이메일
                         password         varchar(100),             #비밀번호
                         name             varchar(100),             #이름
                         phone_number     varchar(100),             #폰번호
                         postal_code        int,                    #우편번호
                         address          varchar(200),             #주소
                         extra_Address    varchar(200),             #부가주소
                         detail_Address   varchar(200),             #상세주소
                         is_business        boolean,                #사업자여부
                         is_active          boolean,                #탈퇴여부
                         join_Date        date,                     #가입날짜
                         leave_Date       date,                     #탈퇴날짜
                         business_number  varchar(100),             #사업자번호
                         upt_date         date                      #수정일자
);


SELECT * FROM CTG_MEMBER;

UPDATE CTG_MEMBER SET IS_ACTIVE = true WHERE USER_ID = '2023092243';

DELETE FROM CTG_MEMBER WHERE USER_ID = '2023093049';

drop TABLE CTG_MEMBER;

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

DELETE from CTG_MEMBER where USER_ID =2023092244;

COMMIT;
-- mybatis 회원가입 양식
# INSERT INTO CTG_MEMBER VALUES (user_id,email,password,name,PHONENUMBER,POSTCODE,ADDRESS,EXTRAADDRESS,DETAILADDRESS,bnCheck,STATE,JOINDATE,DELDATE);

INSERT INTO CTG_MEMBER VALUES (
                               CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),nextval('seq_member')),
                               'email',
                               'password',
                               'name',
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

INSERT INTO  CTG_USER_BS VALUES (#{user_id,jdbcType=VARCHAR},#{b_no,jdbcType=VARCHAR})
;

SELECT COUNT(*) FROM CTG_USER_BS WHERE B_NO = #{b_no,jdbcType=VARCHAR}


SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),nextval('seq_member')) FROM DUAL;

DELETE FROM CTG_MEMBER;

COMMIT ;



-- 회원 이메일 중복여부
SELECT COUNT(*) FROM CTG_USER_BS
where EMAIL = '';


SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m%d'),'-','B-',0) FROM DUAL;

SELECT user_id,NAME,EMAIL,PASSWORD,BNCHECK FROM CTG_MEMBER
WHERE STATE = 1
  AND EMAIL = 'z@nate.com';

SELECT * FROM CTG_MEMBER;

update FLEAMARKET set CURCNT = 200 WHERE FNO = 78;

UPDATE CTG_MEMBER SET PASSWORD = 'SDFSDFS'
WHERE USER_ID =
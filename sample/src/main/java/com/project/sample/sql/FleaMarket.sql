
CREATE TABLE FLEAMARKET(
    fno int PRIMARY KEY AUTO_INCREMENT,
    userno varchar(100),
    email varchar(100),
    title varchar(200),
    content varchar(10000),
    address varchar(100),
    detailAddress varchar(100),
    approvalCnt int,
    curCnt int,
    endDate varchar(20),
    regDate DATE,
    uptDate DATE
);


CREATE TABLE FLEAMARKET_FILES(
    fno int,
    origin_file_name varchar(100),
    uuid_file_name varchar(100)


);

CREATE table application_FM(
    ano int PRIMARY KEY AUTO_INCREMENT,
    fno int,
    userno varchar(100),
    regDate DATE,
    state varchar(20)
);

DROP TABLE application_FM;

-- 파일심기
INSERT INTO FLEAMARKET_FILES VALUES (
    #{fno,jdbcType=NUMERIC},
    #{origin_file_name,jdbcType=VARCHAR},
    #{uuid_file_name,jdbcType=VARCHAR}
);

SELECT * from FLEAMARKET;
SELECT * from FLEAMARKET_FILES;
SELECT * from APPLICATION_FM;

SELECT * FROM FLEAMARKET
WHERE CONCAT(TITLE,ADDRESS,DETAILADDRESS) LIKE CONCAT('%','대림','%');
AND ADDRESS LIKE CONCAT('%','예시','%');


DELETE FROM FLEAMARKET;
DELETE FROM FLEAMARKET_FILES;
DELETE FROM APPLICATION_FM;

select max(FNO) from FLEAMARKET;
-- 리스트조회용

select date_format(sysdate(),'%Y-%m-%d') from FLEAMARKET;

SELECT * FROM FLEAMARKET INNER JOIN (
        select @ROWNUM:= @ROWNUM+1 AS ROWNUM,FILES.* from FLEAMARKET_FILES FILES,(SELECT @ROWNUM :=0) R) TMP
GROUP BY FLEAMARKET.FNO;

select max(fno) from FLEAMARKET;


update FLEAMARKET set ENDDATE = '2023-08-28' where FNO = 58;


-- 전체 게시글에 대해 파일 하나씩들고와서 출력
-- ROWNUM적용시켜서 출력
SELECT
    (@rownum := @rownum + 1) as ROWNUM,
    F.*,
    MIN(F2.UUID_FILE_NAME) AS UUID_FILE_NAME
FROM (SELECT FNO,
             TITLE,
             SUBSTRING_INDEX(ADDRESS, ' ', 3) AS ADDRESS,
             CURCNT,
             DATE_FORMAT(REGDATE, '%Y-%m-%d') AS REGDATE,
             APPROVALCNT,
             ENDDATE,
             CASE WHEN STR_TO_DATE(ENDDATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
                  ELSE '모집중'
                 END AS STATE
      FROM FLEAMARKET
      WHERE CONCAT(TITLE, ADDRESS, DETAILADDRESS) LIKE CONCAT('%', '', '%')
      ORDER BY REGDATE DESC, STR_TO_DATE(ENDDATE, '%Y-%m-%d') ASC
          ) AS F LEFT JOIN (
            SELECT FILES.FNO, FILES.UUID_FILE_NAME
            FROM FLEAMARKET_FILES FILES
            ORDER BY FILES.UUID_FILE_NAME
        ) AS F2 ON F.FNO = F2.FNO,
          (SELECT @rownum := 0) r
      GROUP BY F.FNO
      HAVING ROWNUM BETWEEN 1 and 6
      ORDER BY ROWNUM;

-- main 게시글 리스트 조건문 입력하여 출력

SELECT
    (@rownum := @rownum + 1) as ROWNUM,
    F.*,
    MIN(F2.UUID_FILE_NAME) AS UUID_FILE_NAME
FROM (SELECT  FNO,
              TITLE,
              SUBSTRING_INDEX(ADDRESS, ' ', 3) as ADDRESS,
              CURCNT,
              date_format(REGDATE,'%Y-%m-%d') REGDATE,
              ENDDATE
              APPROVALCNT FROM FLEAMARKET
      -- 검색조건문 구간
      WHERE TITLE LIKE CONCAT('%','','%')

     ) AS F LEFT JOIN (
    SELECT FILES.FNO, FILES.UUID_FILE_NAME
    FROM FLEAMARKET_FILES FILES
    ORDER BY FILES.UUID_FILE_NAME
) AS F2 ON F.FNO = F2.FNO,
     (SELECT @rownum := 0) r
GROUP BY F.FNO
HAVING ROWNUM BETWEEN 1 AND 6;


-- 게시글 수정
update FLEAMARKET SET
         title = 'd',
         content = 'd',
         ADDRESS = 'd',
         DETAILADDRESS = 'd',
         APPROVALCNT = 50,
         ENDDATE ='d',
         UPTDATE = date_format(sysdate(),'%Y-%m-%d')
where fno = 31;


#신청중복검사
SELECT  count(*) from APPLICATION_FM
WHERE FNO = 10 AND userno = userno;

# 신청하기
INSERT INTO APPLICATION_FM VALUES (
    0,FNO,userno,sysdate(),'대기'
);

#신청글 삭제(공통)
DELETE FROM APPLICATION_FM WHERE FNO = #{fno,jdbcType=NUMERIC}


SELECT  FNO,
        TITLE,
        SUBSTRING_INDEX(ADDRESS, ' ', 3) as ADDRESS,
        CURCNT,
        date_format(REGDATE,'%Y-%m-%d') REGDATE,
        APPROVALCNT,ENDDATE FROM FLEAMARKET
                                 -- 검색조건문 구간
WHERE CONCAT(TITLE,ADDRESS,DETAILADDRESS) LIKE  CONCAT('%','','%')
ORDER BY REGDATE DESC,STR_TO_DATE(ENDDATE, '%Y-%m-%d') ASC;
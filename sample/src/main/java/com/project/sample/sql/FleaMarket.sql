
CREATE TABLE FLEAMARKET(
                           post_id int PRIMARY KEY AUTO_INCREMENT,
                           user_id varchar(100),
                           title varchar(200),
                           content varchar(5000),
                           location varchar(200),
                           sub_location varchar(200),
                           max_applicants int,
                           current_count int,
                           end_Date varchar(20),
                           reg_Date DATE,
                           upt_Date DATE
);

DROP TABLE FLEAMARKET;

CREATE TABLE FLEAMARKET_FILES(
                                 file_id int AUTO_INCREMENT PRIMARY KEY ,
                                 post_id int,
                                 origin_file_name varchar(100),
                                 uuid_file_name varchar(100)


);

DROP TABLE FLEAMARKET_FILES;

CREATE table application_FM(
                               applicant_id int PRIMARY KEY AUTO_INCREMENT,
                               post_id int,
                               user_id varchar(100),
                               reg_date DATE,
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

INSERT INTO FLEAMARKET_FILES VALUES (
    0,
        104,
        '다운로드3_1.jpg',
        'e981yqhrsd31ajkf8tyq9g-390478iragjk-_다운로드.jpg'
);

INSERT INTO FLEAMARKET VALUES (
104,
'1231234',
    '반',
                               '타이틀입니다.',
                               '주소1',
                               '주소2',
                               100,
                               0,
                               sysdate()+1,
                               sysdate(),
                               SYSDATE()
);

SELECT * from FLEAMARKET;

SELECT FNO,ORIGIN_FILE_NAME,UUID_FILE_NAME
FROM FLEAMARKET_FILES
WHERE FNO = 101
ORDER BY FILE_ID DESC
LIMIT 1;

SELECT A.* FROM (
                  SELECT
                      (@ROWNUM := @ROWNUM + 1) AS ROWNUM,
                      F.*
                  FROM (
                           SELECT F.FNO,
                                  TITLE,
                                  SUBSTRING_INDEX(ADDRESS, ' ', 3) AS ADDRESS,
                                  CURCNT,
                                  DATE_FORMAT(REGDATE,'%Y-%m-%d') REGDATE,
                                  APPROVALCNT,ENDDATE,
                                  CASE WHEN STR_TO_DATE(ENDDATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
                                       ELSE '모집중'
                                      END AS STATE
                           FROM FLEAMARKET AS F
                                    INNER JOIN (
                               SELECT FF.*
                               FROM FLEAMARKET_FILES AS FF
                                        INNER JOIN (
                                   SELECT FNO, MAX(FILE_ID) AS MAX_FILE_ID
                                   FROM FLEAMARKET_FILES
                                   GROUP BY FNO
                               ) AS MAX_FILE ON FF.FNO = MAX_FILE.FNO AND FF.FILE_ID = MAX_FILE.MAX_FILE_ID
                               ) AS FILE ON F.FNO = FILE.FNO
                               AND
                                    CONCAT(F.TITLE,F.ADDRESS,F.DETAILADDRESS) LIKE  CONCAT('%','','%')
                       ) AS F, (SELECT @rownum := 0) r
) AS A WHERE ROWNUM BETWEEN 1 AND 10;


SELECT A.*
FROM (
         SELECT (@ROWNUM := @ROWNUM + 1) AS ROWNUM, F.*
         FROM (
                  SELECT F.FNO,
                         TITLE,
                         SUBSTRING_INDEX(ADDRESS, ' ', 3) AS ADDRESS,
                         CURCNT,
                         DATE_FORMAT(REGDATE,'%Y-%m-%d') REGDATE,
                         APPROVALCNT,
                         ENDDATE,
                         CASE WHEN STR_TO_DATE(ENDDATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
                              ELSE '모집중'
                             END AS STATE
                  FROM FLEAMARKET AS F
                           INNER JOIN (
                      SELECT FNO, ORIGIN_FILE_NAME, UUID_FILE_NAME
                      FROM FLEAMARKET_FILES
                      WHERE (FNO, FILE_ID) IN (
                          SELECT FNO, MAX(FILE_ID)
                          FROM FLEAMARKET_FILES
                          GROUP BY FNO
                      )
                  ) AS FILE ON F.FNO = FILE.FNO
                  WHERE CONCAT(F.TITLE,F.ADDRESS,F.DETAILADDRESS) LIKE CONCAT('%','','%')
              ) AS F, (SELECT @rownum := 0) r
     ) AS A
WHERE ROWNUM BETWEEN 1 AND 10;


SELECT F.FNO,
       TITLE,
       SUBSTRING_INDEX(ADDRESS, ' ', 3) AS ADDRESS,
       CURCNT,
       DATE_FORMAT(REGDATE,'%Y-%m-%d') REGDATE,
       APPROVALCNT,
       ENDDATE,
       CASE WHEN STR_TO_DATE(ENDDATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
            ELSE '모집중'
           END AS STATE,
       FILE.ORIGIN_FILE_NAME,
       FILE.UUID_FILE_NAME
FROM FLEAMARKET AS F
         INNER JOIN (
    SELECT FF.FNO, FF.ORIGIN_FILE_NAME, FF.UUID_FILE_NAME
    FROM (
             SELECT FNO, ORIGIN_FILE_NAME, UUID_FILE_NAME,
                    (@rownum := IF(@prev_fno = FNO, @rownum + 1, 1)) AS RN,
                    (@prev_fno := FNO)
             FROM FLEAMARKET_FILES
                      CROSS JOIN (SELECT @rownum := 0, @prev_fno := '') AS R
             ORDER BY FNO ASC, FILE_ID DESC
         ) AS FF
    WHERE RN = 1
) AS FILE ON F.FNO = FILE.FNO;

SELECT MAX(FILE_ID) AS FILE_ID FROM FLEAMARKET_FILES GROUP BY FNO;



SELECT * FROM FLEAMARKET;

SELECT FF.*
FROM FLEAMARKET_FILES AS FF
         INNER JOIN (
    SELECT FNO, MAX(FILE_ID) AS MAX_FILE_ID
    FROM FLEAMARKET_FILES
    GROUP BY FNO
) AS MAX_FILE ON FF.FNO = MAX_FILE.FNO AND FF.FILE_ID = MAX_FILE.MAX_FILE_ID;




SELECT
    ROWNUM,
    FNO,
    TITLE,
    ADDRESS,
    CURCNT,
    REGDATE,
    APPROVALCNT,
    ENDDATE,
    STATE,
    UUID_FILE_NAME,
    ORIGIN_FILE_NAME
FROM (
          SELECT
              (@ROWNUM := @ROWNUM + 1) AS ROWNUM,
              A.*
          FROM (
                   SELECT
                       F.FNO,
                       TITLE,
                       SUBSTRING_INDEX(ADDRESS, ' ', 3) AS ADDRESS,
                       CURCNT,
                       DATE_FORMAT(REGDATE,'%Y-%m-%d') REGDATE,
                       APPROVALCNT,ENDDATE,
                       CASE WHEN STR_TO_DATE(ENDDATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
                            ELSE '모집중'
                           END AS STATE,
                       FILE2.UUID_FILE_NAME,
                       FILE2.ORIGIN_FILE_NAME
                   FROM
                       FLEAMARKET AS F
                           INNER JOIN (
                           SELECT
                               FILE1.FNO,
                               FILE1.ORIGIN_FILE_NAME,
                               FILE1.UUID_FILE_NAME
                           FROM
                               FLEAMARKET_FILES AS FILE1
                                   INNER JOIN (
                                   SELECT
                                       FNO,
                                       MAX(FILE_ID) AS MAX_FILE_ID
                                   FROM
                                       FLEAMARKET_FILES
                                   GROUP BY
                                       FNO
                               ) AS MAX_FILE
                                              ON
                                                          FILE1.FNO = MAX_FILE.FNO
                                                      AND
                                                          FILE1.FILE_ID = MAX_FILE.MAX_FILE_ID
                            ) AS FILE2
                            ON F.FNO = FILE2.FNO
                            AND CONCAT(F.TITLE,F.ADDRESS,F.DETAILADDRESS) LIKE  CONCAT('%','','%')
               ) AS A, (SELECT @rownum := 0) r
) AS F
WHERE F.ROWNUM BETWEEN 1 AND 2;


SELECT
    ROWNUM,
    POST_ID,
    TITLE,
    LOCATION,
    CURRENT_COUNT,
    REG_DATE,
    MAX_APPLICANTS,
    END_DATE,
    STATE,
    UUID_FILE_NAME,
    ORIGIN_FILE_NAME
FROM (
         SELECT
             (@ROWNUM := @ROWNUM + 1) AS ROWNUM,
             A.*
         FROM (
                  SELECT
                      F.POST_ID,
                      TITLE,
                      SUBSTRING_INDEX(LOCATION, ' ', 3) AS LOCATION,
                      CURRENT_COUNT,
                      DATE_FORMAT(REG_DATE,'%Y-%m-%d') AS REG_DATE,
                      MAX_APPLICANTS,
                      CASE WHEN STR_TO_DATE(END_DATE, '%Y-%m-%d') < CURDATE() THEN '모집종료'
                           ELSE '모집중'
                          END AS STATE,
                      END_DATE,
                      FILE2.UUID_FILE_NAME,
                      FILE2.ORIGIN_FILE_NAME
                  FROM
                      FLEAMARKET AS F
                          INNER JOIN (
                          SELECT
                              FILE1.POST_ID,
                              FILE1.ORIGIN_FILE_NAME,
                              FILE1.UUID_FILE_NAME
                          FROM
                              FLEAMARKET_FILES AS FILE1
                                  INNER JOIN (
                                  SELECT
                                      POST_ID,
                                      MAX(FILE_ID) AS MAX_FILE_ID
                                  FROM
                                      FLEAMARKET_FILES
                                  GROUP BY
                                      POST_ID
                              ) AS MAX_FILE
                                             ON
                                                         FILE1.POST_ID = MAX_FILE.POST_ID
                                                     AND
                                                         FILE1.FILE_ID = MAX_FILE.MAX_FILE_ID
                      ) AS FILE2
                                     ON F.POST_ID = FILE2.POST_ID
                                         AND CONCAT(F.TITLE,F.LOCATION,F.SUB_LOCATION) LIKE  CONCAT('%','','%')
                                             ) AS A, (SELECT @rownum := 0) r
              ) AS F;
         WHERE F.ROWNUM BETWEEN

;
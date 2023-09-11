SELECT A.MODL_SEQ
     , A.MODL_NM
     , A.MODL_TP
     , A.TMP_SEQ
FROM TBBS_MODL_INF A
WHERE A.MODL_TP IN ('B_TYPE2');

grant all on *.* to 'root'@'%' identified by '1111' with grant option;
flush privileges;

SHOW variables LIKE '%dir';

DROP USER 'root'@'%';

CREATE USER 'root'@'%' identified BY '1111';

SELECT * FROM TBBS_TMP_DOC_INF;

--   ---------------------------------------------------------------------
CREATE TABLE dㅡㅛㄴCTG_NOTIC (
                               ntno int PRIMARY KEY AUTO_INCREMENT, -- 공지글번호
                               title varchar(200), -- 제목
                               content varchar(10000), -- 내용
                               impwhether boolean, -- 중요여부
                               regdate datetime, -- 등록날짜
                               uptdate datetime, -- 수정날짜
                               deldate datetime, -- 종료날짜
                               filewhether boolean, -- 파일존재여부
                               NoticState boolean,
                               inq_cnt int -- 조회수
);

INSERT INTO pandora3_ojt.ctg_notic values(0,#{title},#{content},{impWhether},SYSDATE(),SYSDATE(),#{delDate},#{fileWhether});

INSERT INTO pandora3_ojt.ctg_notic values(0,'제zzzzzzzzzz4','내용5',true, STR_TO_DATE('2023-07-23', '%Y-%m-%d %H:%i:%s'),SYSDATE(),NULL,FALSE,false,0);

USE pandora3_ojt;

COMMIT;

DROP TABLE ctg_notic ;
SELECT * FROM CTG_NOTIC;
WHERE impwhether = FALSE  -- 중요여부
AND regdate >= STR_TO_DATE('2023-08-20', '%Y-%m-%d %H:%i:%s')
AND  -- 여기에 검색구분값 넣기
ORDER BY noticstate = FALSE, regdate DESC;



COMMIT;


SELECT * FROM TBBS_FL_INF; -- 파일목록

SELECT * FROM tbbs_doc_inf tdi;

SELECT * FROM TBBS_TMP_DOC_INF;

SELECT * FROM TBBS_DOC_ADD_ITM_INF;

SELECT * FROM TSYS_ADM_MNU;

SELECT * FROM ctg_notic cn;



SELECT V.*
     ,CEIL(V.ID / 10) PAGE
     ,@ROW_NUMBER TOTAL_COUNT
     ,CEIL(@ROW_NUMBER / 10) TOTAL_PAGE
FROM (
         SELECT Y.*
              ,(@ROW_NUMBER := @ROW_NUMBER + 1 ) ID
              , CASE WHEN QA_CNT > 0  THEN '3'
                     ELSE '2'
             END AS TREATMENT_CD
         FROM (

                  SELECT D.MODL_SEQ
                       , D.DOC_SEQ
                       , D.LGN_ID
                       , (SELECT USR_NM FROM TMBR_ADM_LGN_INF WHERE USR_ID = D.LGN_ID) AS USR_NM
                       , D.TITL
                       , D.NOTC_YN
                       , D.OTP_STAT
                       , D.INQ_CNT
                       , D.CTEGRY_MST_CD
                       , D.CTEGRY_DTL_CD
                       , IFNULL((SELECT CTEGRY_MST_NM FROM TBBS_CTEGRY_MST WHERE CTEGRY_MST_CD = D.CTEGRY_MST_CD), '없음') AS CTEGRY_MST_NM
                       , IFNULL((SELECT CTEGRY_DTL_NM FROM TBBS_CTEGRY_DTL WHERE CTEGRY_MST_CD = D.CTEGRY_MST_CD AND CTEGRY_DTL_CD = D.CTEGRY_DTL_CD), '없음') AS CTEGRY_DTL_NM
                       , (CASE WHEN D.DSPLY_YN IS NULL || D.DSPLY_YN = 'N' THEN '임시저장' WHEN D.DSPLY_ED_DTTM > NOW() THEN '게시중' ELSE '게시종료' END) AS DSPLY_STAT
                       , M.MODL_TP
                       , M.MODL_NM
                       , M.TMP_SEQ
                       , (SELECT COUNT(1) FROM TBBS_QA_CMT_INF QA_CMT WHERE QA_CMT.MODL_SEQ = D.MODL_SEQ AND QA_CMT.DOC_SEQ = D.DOC_SEQ) AS QA_CNT
                       , C.CD_NM
                       , C.REF_1
                       , CTS
                       , CONCAT(SUBSTR(D.CRT_DTTM,1,4), '-', SUBSTR(D.CRT_DTTM,6,2), '-', SUBSTR(D.CRT_DTTM,9,2)) AS F_CRT_DTTM
                       , CONCAT(SUBSTR(D.UPD_DTTM,1,4), '-', SUBSTR(D.UPD_DTTM,6,2), '-', SUBSTR(D.UPD_DTTM,9,2)) AS F_UPD_DTTM
                       , (SELECT COUNT(1) FROM TBBS_FL_INF A WHERE A.DOC_SEQ = D.DOC_SEQ AND IFNULL(A.THUMB_YN,'N') = 'N') AS FL_CNT
                       , F.UPL_FL_NM
                       , (SELECT SYS_NM FROM TDSP_SYS_INF WHERE SYS_CD = M.SYS_CD) AS SYS_NM
                  FROM TBBS_DOC_INF D
                           LEFT OUTER JOIN TBBS_MODL_INF M ON (D.MODL_SEQ = M.MODL_SEQ)
                           INNER JOIN TCMN_CD_DTL C ON (M.MODL_TP = C.CD AND C.MST_CD = 'BBS0001')
                           LEFT OUTER JOIN TBBS_FL_INF F ON (F.DOC_SEQ = D.DOC_SEQ AND THUMB_YN = 'Y')
                  WHERE 1=1
                  ORDER BY D.DOC_SEQ DESC ) Y,(SELECT @ROW_NUMBER := 0) RN
         WHERE 1=1
     ) V
ORDER BY V.DOC_SEQ DESC;


SELECT V.*
     ,CEIL(V.ID / 3) PAGE
     ,@ROW_NUMBER TOTAL_COUNT
     ,CEIL(@ROW_NUMBER / 3) TOTAL_PAGE
FROM (
         SELECT Y.*
              ,(@ROW_NUMBER := @ROW_NUMBER + 1 ) ID
         FROM (

                  SELECT * FROM ctg_notic cn  ) Y,(SELECT @ROW_NUMBER := 0) RN
         WHERE 1=1
     ) V;


SELECT @ROW_NUMBER := 1;

SELECT STR_TO_DATE('2023-08-23', '%Y-%m-%d %H:%i:%s') FROM DUAL;



UPDATE ctg_notic SET NoticState = FALSE;

DELETE FROM ctg_notic ;
WHERE ntno=26;
WHERE regdate = max(ntno);

SELECT * FROM ctg_notic_files cnf;
WHERE 1=1
			 AND IMPWHETHER = TRUE
			 AND REGDATE >= STR_TO_DATE('2023-08-24', '%Y-%m-%d %H:%i:%s')
             AND TITLE LIKE CONCAT('%',  '', '%')
            -- AND    CONTETN LIKE CONCAT('%','', '%')
            -- AND    (TITLE LIKE CONCAT('%', #{sch_type_txt, jdbcType=VARCHAR}, '%')
           --  OR    CONTENT LIKE CONCAT('%', #{sch_type_txt, jdbcType=VARCHAR}, '%'))
			 ORDER BY noticstate = FALSE, regdate DESC;

SELECT * FROM ctg_notic_files cnf  ;
WHERE impwhether =1;

SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE COLUMN_NAME='NOTICSTATE';
COMMIT;
SET AUTOCOMMIT = TRUE;


UPDATE ctg_notic SET
                     uptdate = sysdate(),
                     title = #{title,jdbcType=VARCHAR},
	content = #{content,jdbcType=VARCHAR},
	impwhether = #{content,jdbcType=BIT},
	deldate  = #{deldate,jdbcType=DATE},
	filewhether = #{fileWhether,jdbcType=BIT},
	NoticState = IF(SELECT DELDATE<sysdate(),FALSE,TRUE)
WHERE ntno = #{ntno,jdbcType=NUMERIC};

UPDATE ctg_notic SET
    NoticState = IF(deldate <sysdate(),FALSE,TRUE);
COMMIT;


DELETE FROM ctg_notic;
DELETE FROM ctg_notic_files;



SELECT * FROM ctg_notic_files cnf ;

WHERE ntno = #{ntno,jdbcType=NUMERIC}

SELECT max(ntno) FROM ctg_notic;
COMMIT;

UPDATE ctg_notic_files
SET ntno = (SELECT max(ntno) FROM ctg_notic )
WHERE ntno = -1;

SELECT * FROM ctg_notic;
SELECT * FROM ctg_notic_files cnf ;

WHERE ntno = 40;
ALTER TABLE ctg_notic  AUTO_INCREMENT = 0;
ALTER TABLE ctg_notic_files change upload_file_name chg_source_filename varchar(1000);


DELETE FROM ctg_notic_files
WHERE ntno = #{ntno,jdbcType=NUMERIC};



-- ===========================================================================================

# 리스트 조회

SELECT (@rownum := @rownum + 1) as ROWNUM,N.* FROM (
    SELECT ntno,title,IMPWHETHER,REGDATE,UPTDATE,DELDATE FROM CTG_NOTIC
    WHERE ctg_notic.TITLE LIKE CONCAT('%','','%')
    ORDER BY IMPWHETHER DESC) AS N,(SELECT @rownum := 0) r
    HAVING ROWNUM BETWEEN #{st_rownum,jdbcType=NUMERIC} and #{en_rownum,jdbcType=NUMERIC}
    ORDER BY ROWNUM








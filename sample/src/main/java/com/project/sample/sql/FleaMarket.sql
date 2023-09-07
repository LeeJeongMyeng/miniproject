

CREATE TABLE FLEAMARKET_FILES(
    fno int,
    origin_file_name varchar(100),
    uuid_file_name varchar(100)


);
-- 파일심기
INSERT INTO FLEAMARKET_FILES VALUES (
    #{fno,jdbcType=NUMERIC},
    #{origin_file_name,jdbcType=VARCHAR},
    #{uuid_file_name,jdbcType=VARCHAR}
);

SELECT * from FLEAMARKET;
SELECT * from FLEAMARKET_FILES;



DELETE FROM FLEAMARKET;
DELETE FROM FLEAMARKET_FILES;

select max(FNO) from FLEAMARKET;
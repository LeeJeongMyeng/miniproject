use pandora3_ojt;
-- 권한 허용
grant all on *.* to 'root'@'%' identified by '1111' with grant option;
flush privileges;

SHOW variables LIKE '%dir';

-- 유저삭제
DROP USER 'root'@'%';

-- 유저생성
CREATE USER 'root'@'%' identified BY '1111';

-- 한글+이모티콘 인코딩
ALTER DATABASE pandora3_ojt CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

--   ---------------------------------------------------------------------
-- 커밋
COMMIT;
-- 롤백
rollback;

-- 오토커밋
set autocommit = true; -- true/false

-- 오토커밋 여부확인
SELECT @@AUTOCOMMIT;
--   ---------------------------------------------------------------------

-- 페이징처리
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


-- rownum 확인
SELECT @ROW_NUMBER := 1;












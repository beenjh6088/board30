CREATE TABLE T_BOARD
(
  ARTICLENO      NUMBER(10),
  PARENTNO       NUMBER(10)                     DEFAULT 0,
  TITLE          VARCHAR2(500 BYTE),
  CONTENT        VARCHAR2(4000 BYTE),
  IMAGEFILENAME  VARCHAR2(100 BYTE),
  WRITEDATE      DATE                           DEFAULT SYSDATE,
  ID             VARCHAR2(10 BYTE)
)
LOGGING 
NOCOMPRESS 
NOCACHE
RESULT_CACHE (MODE DEFAULT)
NOPARALLEL
MONITORING;

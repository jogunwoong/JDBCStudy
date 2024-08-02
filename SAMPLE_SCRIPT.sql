-- JDBC�� ���� : C##JDBC/JDBC
-- ��������
CREATE USER C##JDBC IDENTIFIED BY JDBC;
-- ���� �ο�
GRANT CONNECT, RESOURCE TO C##JDBC;

ALTER USER C##JDBC DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
--������ �������� ���� ���� ����
------------------------------------------------
--ȸ�������� ������ ���̺�
DROP TABLE MEMBER;
CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,--ȸ����ȣ
    USERID VARCHAR2(20)NOT NULL UNIQUE,--ȸ�� ���̵�
    USERPW VARCHAR2(20)NOT NULL,--ȸ�� ��й�ȣ
    USERNAME VARCHAR(20)NOT NULL,--�̸�
    GENDER CHAR(1)CHECK(GENDER IN('M','F')),--����
    AGE NUMBER,--����
    EMAIL VARCHAR2(30),--�̸���
    ADDRESS VARCHAR2(100),--�ּ�
    PHONE VARCHAR2(13),
    HOBBY VARCHAR2(50),--���
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL--������
);

--ȸ����ȣ�� ����� ������ ����
DROP SEQUENCE SEQ USERNO;
CREATE SEQUENCE SEQ_USERNO
NOCACHE;

--���õ����� 2�� �߰�
INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'admin', '1234', '������', 'M', 20, 'admin@kh.or.kr', '����', '010-1010-0101', null, '2020-07-30');
INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'JOGUNWOONG', '1234', '���ǿ�', 'M', 26, 'jgw0928@kh.or.kr', '��õ', '010-9999-9999', null, DEFAULT);
    
COMMIT;
----------------------------------------------
-- �׽�Ʈ�� ���̺�(TEST)
CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(30),
    TDATE DATE
);
SELECT * FROM TEST;

INSERT INTO TEST VALUES(1, '��ٿ�', SYSDATE);
COMMIT;









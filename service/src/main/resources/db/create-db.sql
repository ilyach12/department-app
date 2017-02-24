CREATE TABLE IF NOT EXISTS DEPARTMENT
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    DEPARTMENTNAME VARCHAR(2147483647) NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS "DEPARTMENT_ID_uindex" ON DEPARTMENT (ID);
CREATE UNIQUE INDEX IF NOT EXISTS "DEPARTMENT_DEPARTMENTNAME_uindex" ON DEPARTMENT (DEPARTMENTNAME);

CREATE TABLE IF NOT EXISTS EMPLOYEES
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    DEPARTMENT_ID BIGINT NOT NULL,
    FULLNAME VARCHAR(2147483647) NOT NULL,
    BIRTHDAY DATE,
    SALARY INT NOT NULL,
    CONSTRAINT EMPLOYEES_DEPARTMENT__FK FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT (ID) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS "EMPLOYEES_ID_uindex" ON EMPLOYEES (ID);
CREATE INDEX IF NOT EXISTS EMPLOYEES_DEPARTMENT__FK_INDEX_8 ON EMPLOYEES (DEPARTMENT_ID)
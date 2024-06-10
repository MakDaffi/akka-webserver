CREATE DATABASE akkadb;

\c akkadb

CREATE TABLE passport (
    id UUID NOT NULL PRIMARY KEY,
    series INT NOT NULL,
    number BIGINT NOT NULL,
    nameIssued VARCHAR(128) NOT NULL,
    departmentCode INT NOT NULL,
    registrationPlace VARCHAR(128) NOT NULL,
    visaPlace VARCHAR(128) NOT NULL
);

INSERT INTO passport (id, series, number, nameIssued, departmentCode, registrationPlace, visaPlace) 
VALUES ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 6753, 200102, 'ABOBA', 300, 'Podolsk', 'Brazil'), ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61', 6763, 210102, 'ABOBA', 288, 'Podolsk', 'Brazil');

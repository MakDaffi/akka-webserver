CREATE DATABASE akkadb;

\c akkadb

CREATE TABLE passport (
    passportId UUID NOT NULL PRIMARY KEY,
    series INT NOT NULL,
    number BIGINT NOT NULL,
    nameIssued VARCHAR(128) NOT NULL,
    departmentCode INT NOT NULL,
    registrationPlace VARCHAR(128) NOT NULL,
    visaPlace VARCHAR(128) NOT NULL
);

INSERT INTO passport (passportId, series, number, nameIssued, departmentCode, registrationPlace, visaPlace) 
VALUES ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 6753, 200102, 'ABOBA', 300, 'Podolsk', 'Brazil'), ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61', 6763, 210102, 'ABOBA', 288, 'Podolsk', 'Brazil');

CREATE TABLE systemUser (
    userId UUID NOT NULL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64) NOT NULL,
    passportId UUID NOT NULL REFERENCES passport(passportId)
);

INSERT INTO systemUser (userId, name, surname, patronymic, passportId) 
VALUES ('9b3deb4d-7b7d-4bad-9bdd-2b0d7b3dcb6d', 'Sergey', 'Okunkov', 'Victorovich', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d'), ('9b3deb5d-3b7d-5bad-9bdd-2b0d7b3dcb6d', 'Boba', 'Aboba', 'Bibovich', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61');

CREATE VIEW passportUser AS
SELECT p.passportId, p.series, p.number, p.nameIssued, p.departmentCode, p.registrationPlace, p.visaPlace, u.name, u.surname, u.patronymic
FROM systemUser as u INNER JOIN passport as p
ON p.passportId = u.passportId;
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

CREATE TABLE systemUser (
    userId UUID NOT NULL PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64) NOT NULL,
    passportId UUID NOT NULL REFERENCES passport(passportId)
);

CREATE VIEW passportUser AS
SELECT p.passportId, p.series, p.number, p.nameIssued, p.departmentCode, p.registrationPlace, p.visaPlace, u.name, u.surname, u.patronymic
FROM systemUser as u INNER JOIN passport as p
ON p.passportId = u.passportId;
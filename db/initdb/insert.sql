\c akkadb

INSERT INTO passport (passportId, series, number, nameIssued, departmentCode, registrationPlace, visaPlace) 
VALUES ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 6753, 200102, 'ABOBA', 300, 'Podolsk', 'Brazil'), ('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61', 6763, 210102, 'ABOBA', 288, 'Podolsk', 'Brazil');

INSERT INTO systemUser (userId, name, surname, patronymic, passportId) 
VALUES ('9b3deb4d-7b7d-4bad-9bdd-2b0d7b3dcb6d', 'Sergey', 'Okunkov', 'Victorovich', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d'), ('9b3deb5d-3b7d-5bad-9bdd-2b0d7b3dcb6d', 'Boba', 'Aboba', 'Bibovich', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61');
package database

object Queries {
    val SELECT_PASSPORTS = "SELECT * FROM passport;"
    val SELECT_USER_DATA_BY_PASSPORT_ID = "SELECT * FROM passportUser WHERE passportId='%s'"
    val SELECT_USER_BY_PASSPORT_ID = "SELECT * FROM systemUser WHERE passportId='%s'"

    val DELETE_USER_DATA_BY_PASSPORT_ID = "DELETE FROM systemUser WHERE passportId='%s'; DELETE FROM passport WHERE passportId='%s'"

    val INSERT_PASSPORT = "INSERT INTO passport (passportId, series, number, nameIssued, departmentCode, registrationPlace, visaPlace) VALUES ('%s', %d, %d, '%s', %d, '%s', '%s')"
    val INSERT_USER = "INSERT INTO systemUser (userId, name, surname, patronymic, passportId) VALUES ('%s', '%s', '%s', '%s', '%s')"

    val UPDATE_PASSPORT = "UPDATE passport SET series=%d, number=%d, nameIssued='%s', departmentCode=%d, registrationPlace='%s', visaPlace='%s' WHERE passportId='%s'"
    val UPDATE_USER = "UPDATE systemUser SET name='%s', surname='%s', patronymic='%s' WHERE passportId='%s'"
}
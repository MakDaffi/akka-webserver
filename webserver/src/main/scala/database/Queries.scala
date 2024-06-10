package database

object Queries {
    val SELECT_PASSPORTS = "SELECT * FROM passport;"
    val SELECT_USER_DATA_BY_PASSPORT_ID = "SELECT * FROM passportUser WHERE passportId='%s'"
    val SELECT_USER_BY_PASSPORT_ID = "SELECT * FROM systemUser WHERE passportId='%s'"
}
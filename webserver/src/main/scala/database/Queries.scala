package database

object Queries {
    val SELECT_PASSPORTS = "SELECT  * FROM passport;"
    val SELECT_PASSPORT_BY_ID = "SELECT  * FROM passport WHERE id='%s'"
}
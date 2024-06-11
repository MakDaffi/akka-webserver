package entities

import java.sql.ResultSet

case class Passport(passportId: String, series: Int, number: Long, nameIssued: String, 
                    departmentCode: Int, registrationPlace: String, visaPlace: String)

object Passport {
    def fromResultSet(resultSet: ResultSet): Passport = {
        val passportId: String = resultSet.getString("passportId")
        val series: Int = resultSet.getInt("series")
        val number: Long = resultSet.getLong("number")
        val nameIssued: String = resultSet.getString("nameIssued")
        val departmentCode: Int = resultSet.getInt("departmentCode")
        val registrationPlace: String = resultSet.getString("registrationPlace")
        val visaPlace: String = resultSet.getString("visaPlace")
        return new Passport(passportId, series, number, nameIssued, departmentCode, registrationPlace, visaPlace)
    }
}
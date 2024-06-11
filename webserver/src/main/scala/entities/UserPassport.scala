package entities

import java.sql.ResultSet

case class UserPassport(passportId: String, series: Int, number: Long,
                        nameIssued: String, departmentCode: Int,
                        registrationPlace: String, visaPlace: String, name: String,
                        surname: String, patronymic: String)

object  UserPassport {
    def fromResultSet(resultSet: ResultSet): UserPassport = {
        val passportId: String = resultSet.getString("passportId")
        val series: Int = resultSet.getInt("series")
        val number: Long = resultSet.getLong("number")
        val nameIssued: String = resultSet.getString("nameIssued")
        val departmentCode: Int = resultSet.getInt("departmentCode")
        val registrationPlace: String = resultSet.getString("registrationPlace")
        val visaPlace: String = resultSet.getString("visaPlace")
        val name: String = resultSet.getString("name")
        val surname: String = resultSet.getString("surname")
        val patronymic: String = resultSet.getString("patronymic")
        return new UserPassport(passportId, series, number,
                                nameIssued, departmentCode,
                                registrationPlace, visaPlace, name,
                                surname, patronymic)
    }
}
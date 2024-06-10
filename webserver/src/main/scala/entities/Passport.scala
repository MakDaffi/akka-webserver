package entities

import java.sql.ResultSet

class Passport(private val resultSet: ResultSet) {
    val id: String = resultSet.getString("id")
    val series: Int = resultSet.getInt("series")
    val number: Long = resultSet.getLong("number")
    val nameIssued: String = resultSet.getString("nameIssued")
    val departmentCode: Int = resultSet.getInt("departmentCode")
    val registrationPlace: String = resultSet.getString("registrationPlace")
    val visaPlace: String = resultSet.getString("visaPlace")
}
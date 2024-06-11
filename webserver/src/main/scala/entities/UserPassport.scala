package entities

import java.sql.ResultSet
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol._

case class UserPassport(series: Int, number: Long,
                        nameIssued: String, departmentCode: Int,
                        registrationPlace: String, visaPlace: String, name: String,
                        surname: String, patronymic: String)

object  UserPassport {
    implicit val userJsonFormat: RootJsonFormat[UserPassport] = jsonFormat9(UserPassport.apply)

    def fromResultSet(resultSet: ResultSet): UserPassport = {
        val series: Int = resultSet.getInt("series")
        val number: Long = resultSet.getLong("number")
        val nameIssued: String = resultSet.getString("nameIssued")
        val departmentCode: Int = resultSet.getInt("departmentCode")
        val registrationPlace: String = resultSet.getString("registrationPlace")
        val visaPlace: String = resultSet.getString("visaPlace")
        val name: String = resultSet.getString("name")
        val surname: String = resultSet.getString("surname")
        val patronymic: String = resultSet.getString("patronymic")
        return new UserPassport(series, number,
                                nameIssued, departmentCode,
                                registrationPlace, visaPlace, name,
                                surname, patronymic)
    }
}
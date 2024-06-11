package entities

import java.sql.ResultSet

case class User(name: String, surname: String, patronymic: String)

object User {
    def fromResultSet(resultSet: ResultSet): User = {
        val name: String = resultSet.getString("name")
        val surname: String = resultSet.getString("surname")
        val patronymic: String = resultSet.getString("patronymic")
        return new User(name, surname, patronymic)
    }
}
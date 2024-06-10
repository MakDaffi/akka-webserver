package entities

import java.sql.ResultSet

class User(private val resultSet: ResultSet) {
    val name: String = resultSet.getString("name")
    val surname: String = resultSet.getString("surname")
    val patronymic: String = resultSet.getString("patronymic")
}
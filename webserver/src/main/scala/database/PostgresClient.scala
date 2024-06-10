package database

import java.sql.{Connection, DriverManager}
import entities.{Passport, UserPassport, User}

class PostgresClient
(
    val host: String,
    val port: Int,
    val databaseName: String,
    val username: String,
    val password: String
) 
{

    private val connectionString = s"jdbc:postgresql://$host:$port/$databaseName"
    private val connection: Connection = DriverManager.getConnection(connectionString, username, password)

    def selectPassports(): List[Passport] = {
        val statement = connection.createStatement()
        val passportRaw = statement.executeQuery(Queries.SELECT_PASSPORTS)
        var passportList = List[Passport]()
        while(passportRaw.next) {
            var passport = new Passport(passportRaw)
            passportList = passportList :+ passport
        }
        return passportList
    }

    def selectUserDataByPassportId(id: String): UserPassport = {
        val statement = connection.createStatement()
        val userPassportRaw = statement.executeQuery(Queries.SELECT_USER_DATA_BY_PASSPORT_ID.format(id))
        var userPassportList = List[UserPassport]()
        while(userPassportRaw.next) {
            var userPassport = new UserPassport(userPassportRaw)
            userPassportList = userPassportList :+ userPassport
        }
        if (userPassportList.length != 0) {
            return userPassportList(0)
        } else {
            return null
        }
    }

    def selectUserByPassportId(id: String): User = {
        val statement = connection.createStatement()
        val userRaw = statement.executeQuery(Queries.SELECT_USER_BY_PASSPORT_ID.format(id))
        var userList = List[User]()
        while(userRaw.next) {
            var user = new User(userRaw)
            userList = userList :+ user
        }
        if (userList.length != 0) {
            return userList(0)
        } else {
            return null
        }
    }
                    
}
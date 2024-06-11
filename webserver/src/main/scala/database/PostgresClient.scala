package database

import java.sql.{Connection, DriverManager}
import entities.{Passport, UserPassport, User}
import org.postgresql.util.PSQLException

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

    def selectUserDataByPassportId(id: String): Tuple2[UserPassport, Throwable] = {
        val statement = connection.createStatement()
        try {
            val userPassportRaw = statement.executeQuery(Queries.SELECT_USER_DATA_BY_PASSPORT_ID.format(id))
            var userPassportList = List[UserPassport]()
            while(userPassportRaw.next) {
                var userPassport = new UserPassport(userPassportRaw)
                userPassportList = userPassportList :+ userPassport
            }
            if (userPassportList.length != 0) {
                return new Tuple2(userPassportList(0), null)
            } else {
                return new Tuple2(null, null)
            }
        } catch {
            case e: PSQLException => return new Tuple2(null, e)
        }
    }

    def selectUserByPassportId(id: String): Tuple2[User, Throwable] = {
        val statement = connection.createStatement()
        try {
            val userRaw = statement.executeQuery(Queries.SELECT_USER_BY_PASSPORT_ID.format(id))
            var userList = List[User]()
            while(userRaw.next) {
                var user = new User(userRaw)
                userList = userList :+ user
            }
            if (userList.length != 0) {
                return new Tuple2(userList(0), null)
            } else {
                return new Tuple2(null, null)
            }
        } catch {
            case e: PSQLException => return new Tuple2(null, e)
        }
    }

    def deleteUserDataByPassportId(id: String): Boolean = {
        val statement = connection.createStatement()
        val result = statement.executeUpdate(Queries.DELETE_USER_DATA_BY_PASSPORT_ID.format(id, id))
        return if (result != 0) true else false
    }
                    
}
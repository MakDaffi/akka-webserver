package database

import java.sql.{Connection, DriverManager}
import entities.Passport

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
                    
}
package server

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import database.PostgresClient

import scala.util.Failure
import scala.util.Success
import java.lang.String

object AkkaPassportApp {
  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    import system.executionContext

    val futureBinding = Http().bindAndHandle(routes, "localhost", 8050)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }

  def main(args: Array[String]): Unit = {
    val postgresClient = new PostgresClient("localhost", 5432, "akkadb", "postgres", "1234")
    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val routes = 
        path("ping") {
          get {
            context.system.log.info("Gotten /pong request")
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "pong"))
          }
        } ~
        path("api" / "passports") {
          get {
            context.system.log.info("Gotten /api/passports request")
            val passportList = postgresClient.selectPassports()
            val json = JsonUtils.apiPassportOutputToJson(passportList)
            complete(
              HttpResponse(
                OK,
                entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
              )
            )
          }
        } ~ 
        path("api" / "passports" / Segment) { id =>
            get {
              context.system.log.info(s"Gotten /api/passports/$id request")
              val userPassport= postgresClient.selectUserDataByPassportId(id)
              if (userPassport != null) {
                val json = JsonUtils.userPassportToJson(userPassport)
                complete(
                  HttpResponse(
                    OK,
                    entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
                  )
                )
              } else {
                complete(HttpResponse(NoContent))
              }
          }
        } ~ 
        path("api" / "passports" / Segment / "short") { id =>
            get {
              context.system.log.info(s"Gotten /api/passports/$id/short request")
              val user= postgresClient.selectUserByPassportId(id)
              if (user != null) {
                val json = JsonUtils.userToJson(user)
                complete(
                  HttpResponse(
                    OK,
                    entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
                  )
                )
              } else {
                complete(HttpResponse(NoContent))
              }
          }
        }
      startHttpServer(routes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
  }
}

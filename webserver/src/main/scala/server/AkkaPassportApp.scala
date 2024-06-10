package server

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import database.PostgresClient

import scala.util.Failure
import scala.util.Success

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
            val json = JsonUtils.apiPassportOutputToJson(passportList).prettyPrint
            complete(HttpEntity(ContentTypes.`application/json`, json))
          }
        }
      startHttpServer(routes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
  }
}

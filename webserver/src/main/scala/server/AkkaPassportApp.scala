package server

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport. _
import database.PostgresClient
import entities._

import scala.util.Failure
import scala.util.Success
import java.lang.String

object AkkaPassportApp {
  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {
    import system.executionContext

    val config = ConfigUtils.config()
    val futureBinding = Http().bindAndHandle(routes, config.getString("api.host"), config.getInt("api.port"))
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
    val config = ConfigUtils.config()
    val postgresClient = new PostgresClient(
      config.getString("database.host"), config.getInt("database.port"), config.getString("database.name"),
      config.getString("database.creds.username"), config.getString("database.creds.password")
    )
    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val routes = 
        path("ping") {
          get {
            context.system.log.info("Gotten GET /pong request")
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "pong"))
          }
        } ~
        path("api" / "passports") {
          get {
            context.system.log.info("Gotten GET /api/passports request")
            val passportList = postgresClient.selectPassports()
            context.system.log.debug(s"Successful selected ${passportList.length} passports")
            val json = JsonUtils.apiPassportOutputToJson(passportList)
            complete(
              HttpResponse(
                OK,
                entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
              )
            )
          }
        } ~ 
        path("api" / "passports" / Segment / "short") { id =>
            get {
              context.system.log.info(s"Gotten GET /api/passports/$id/short request")
              val user = postgresClient.selectUserByPassportId(id)
              if (user._2 != null) {
                context.system.log.error(s"Validate error. Invalid UUID: ${id}")
                complete(
                    HttpResponse(
                      UnprocessableEntity,
                      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"$id is not UUID")
                    )
                )
              } else {
                if (user._1 != null) {
                  val json = JsonUtils.userToJson(user._1)
                  complete(
                    HttpResponse(
                      OK,
                      entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
                    )
                  )
                } else {
                  context.system.log.warn(s"Nothing found by UUID: ${id}")
                  complete(HttpResponse(NotFound))
                }
              }
          }
        } ~
        path("api" / "passports" / Segment) { id =>
          get {
              context.system.log.info(s"Gotten GET /api/passports/$id request")
              val userPassport = postgresClient.selectUserDataByPassportId(id)
              if (userPassport._2 != null) {
                context.system.log.error(s"Validate error. Invalid UUID: ${id}")
                complete(
                    HttpResponse(
                      UnprocessableEntity,
                      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"$id is not UUID")
                    )
                )
              } else {
                if (userPassport._1 != null) {
                  val json = JsonUtils.userPassportToJson(userPassport._1)
                  complete(
                    HttpResponse(
                      OK,
                      entity = HttpEntity(ContentTypes.`application/json`, json.prettyPrint)
                    )
                  )
                } else {
                  context.system.log.warn(s"Nothing found by UUID: ${id}")
                  complete(HttpResponse(
                    NotFound,
                    entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"Record with $id doesn't exist!")
                  )
                )
                }
              }
          } ~
            delete {
              context.system.log.info(s"Gotten DELETE /api/passports/$id request")
              val isNotError = postgresClient.deleteUserDataByPassportId(id)
              if (isNotError) {
                complete(HttpResponse(
                    NoContent,
                    entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"Record with $id doesn't exist!")
                  )
                )
              } else {
                context.system.log.warn(s"Nothing found by UUID: ${id}")
                complete(HttpResponse(
                    NotFound,
                    entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"Record with $id doesn't exist!")
                  )
                )
              }
          } ~
            post {
              context.system.log.info(s"Gotten POST /api/passports/$id request")
              entity(as[UserPassport]) { userPassport =>
                context.system.log.debug(s"Request entity: $userPassport")
                val isNotError = postgresClient.insertUserData(id, userPassport)
                if (isNotError) {
                  complete(HttpResponse(NoContent))
                } else {
                  context.system.log.warn(s"Record with UUID ${id} already exist")
                  complete(
                    HttpResponse(
                      Conflict,
                      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"Record with UUID ${id} already exist!")
                    )
                  )
                }
              }
            } ~
            put {
              context.system.log.info(s"Gotten PUT /api/passports/$id request")
              entity(as[UserPassport]) { userPassport =>
                context.system.log.debug(s"Request entity: $userPassport")
                val isNotError = postgresClient.updateUserData(id, userPassport)
                if (isNotError) {
                  complete(HttpResponse(NoContent))
                } else {
                  context.system.log.warn(s"Record with UUID ${id} already exist")
                  complete(
                    HttpResponse(
                      NotFound,
                      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"Record with $id doesn't exist!")
                    )
                  )
                }
              }
            }
        }
      startHttpServer(routes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")
  }
}


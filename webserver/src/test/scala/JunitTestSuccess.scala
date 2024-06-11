import org.junit.Test
import org.junit.Assert._

import server.ConfigUtils

import sttp.client4.quick._
import sttp.client4.Response
import sttp.model.StatusCode

class JunitTestsSuccess {
  val host = ConfigUtils.config().getString("api.host") 
  val port = ConfigUtils.config().getInt("api.port")

  @Test
  def testPingRequest = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/ping")
      .send()
    assertTrue(response.code == StatusCode.Ok)
    assertTrue(response.body == "pong")
  }

  @Test
  def testGetPassports = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports")
      .send()
    assertTrue(response.code == StatusCode.Ok)
    assertTrue(response.body != "[]")
  }

  @Test
  def testGetPassportById = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
      .send()
    assertTrue(response.code == StatusCode.Ok)
  }

  @Test
  def testGetPassportShortById = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d/short")
      .send()
    assertTrue(response.code == StatusCode.Ok)
  }

  @Test
  def testDeletePassportById = {
    val response: Response[String] = quickRequest
      .delete(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb61")
      .send()
    assertTrue(response.code == StatusCode.NoContent)
  }
}
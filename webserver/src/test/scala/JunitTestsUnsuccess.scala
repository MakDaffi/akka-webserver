import org.junit.Test
import org.junit.Assert._

import server.ConfigUtils

import sttp.client4.quick._
import sttp.client4.Response
import sttp.model.StatusCode

class JunitTestsUnsuccess {
  val host = ConfigUtils.config().getString("api.host") 
  val port = ConfigUtils.config().getInt("api.port")

  @Test
  def testWrongEndpointRequest = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/test")
      .send()
    assertTrue(response.code == StatusCode.NotFound)
  }

  @Test
  def testGetPassportByNonExistedId = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb3d")
      .send()
    assertTrue(response.code == StatusCode.NotFound)
  }

  @Test
  def testGetPassportByWrongId = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/aboba")
      .send()
    assertTrue(response.code == StatusCode.UnprocessableEntity)
  }

  @Test
  def testGetPassportShortByNonExistedId = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb3d/short")
      .send()
    assertTrue(response.code == StatusCode.NotFound)
  }

  @Test
  def testGetPassportShortByWrongId = {
    val response: Response[String] = quickRequest
      .get(uri"http://$host:$port/api/passports/aboba/short")
      .send()
    assertTrue(response.code == StatusCode.UnprocessableEntity)
  }

  @Test
  def testDeletePassportByNonExistedId = {
    val response: Response[String] = quickRequest
      .delete(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb3d")
      .send()
    assertTrue(response.code == StatusCode.NotFound)
  }

  @Test
  def testPostPassportByIdWithEmptyBody = {
    val response: Response[String] = quickRequest
      .post(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb3d")
      .send()
    assertTrue(response.code == StatusCode.BadRequest)
  }

  @Test
  def testPutPassportByIdWithEmptyBody = {
    val response: Response[String] = quickRequest
      .put(uri"http://$host:$port/api/passports/9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb3d")
      .send()
    assertTrue(response.code == StatusCode.BadRequest)
  }
}

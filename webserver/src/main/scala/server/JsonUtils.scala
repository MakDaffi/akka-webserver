package server

import spray.json._
import entities._

object JsonUtils {
    implicit def passportToJson(value: Passport): JsValue = {
        JsObject(
            "id" -> JsString(value.id),
            "series" -> JsNumber(value.series),
            "number" -> JsNumber(value.number),
            "nameIssued" -> JsString(value.nameIssued),
            "departmentCode" -> JsNumber(value.departmentCode),
            "registrationPlace" -> JsString(value.registrationPlace),
            "visaPlace" -> JsString(value.visaPlace)
        )
    }

    implicit def listToJson[T](value: List[T])(implicit f: T => JsValue): JsValue = {
        JsArray(value.map(f).toVector)
    }

    implicit def apiPassportOutputToJson(value: List[Passport]): JsValue = {
        listToJson(value)(passportToJson(_))
    }
}
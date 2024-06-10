package server

import spray.json._
import entities._

object JsonUtils {
    implicit def passportToJson(value: Passport): JsValue = {
        JsObject(
            "passportId" -> JsString(value.passportId),
            "series" -> JsNumber(value.series),
            "number" -> JsNumber(value.number),
            "nameIssued" -> JsString(value.nameIssued),
            "departmentCode" -> JsNumber(value.departmentCode),
            "registrationPlace" -> JsString(value.registrationPlace),
            "visaPlace" -> JsString(value.visaPlace)
        )
    }

    implicit def userPassportToJson(value: UserPassport): JsValue = {
        JsObject(
            "passportId" -> JsString(value.passportId),
            "series" -> JsNumber(value.series),
            "number" -> JsNumber(value.number),
            "nameIssued" -> JsString(value.nameIssued),
            "departmentCode" -> JsNumber(value.departmentCode),
            "registrationPlace" -> JsString(value.registrationPlace),
            "visaPlace" -> JsString(value.visaPlace),
            "name" -> JsString(value.name),
            "surname" -> JsString(value.surname),
            "patronymic" -> JsString(value.patronymic),
        )
    }

    implicit def userToJson(value: User): JsValue = {
        JsObject(
            "name" -> JsString(value.name),
            "surname" -> JsString(value.surname),
            "patronymic" -> JsString(value.patronymic),
        )
    }

    implicit def listToJson[T](value: List[T])(implicit f: T => JsValue): JsValue = {
        JsArray(value.map(f).toVector)
    }

    implicit def apiPassportOutputToJson(value: List[Passport]): JsValue = {
        listToJson(value)(passportToJson(_))
    }
}
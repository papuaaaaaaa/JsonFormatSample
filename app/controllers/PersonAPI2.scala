package controllers

import models.Person
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

object PersonAPI2 extends Controller {

  import formatters.Formatter2.PersonJsonFormatter

  def register = Action(parse.json) {
    _.body.validate[Person].map { p =>
      //register
      Ok(Json.toJson(p))
    }.recoverTotal { e =>
      BadRequest(JsError.toFlatJson(e))
    }
  }
}
package formatters

import models.{Person, Name}
import play.api.libs.json._

object Formatter1 {

  implicit object NameJsonFormatter extends Format[Name] {
    def reads(json: JsValue): JsResult[Name] = {
      for {
        first <- (json \ "first").validate[String]
        last <- (json \ "last").validate[String]
      } yield new Name(first, last)
    }

    def writes(name : Name) : JsValue = {
      Json.obj(("first", name.first), ("last", name.last))
    }
  }

  implicit object PersonJsonFormatter extends Format[Person] {
    def reads(json: JsValue): JsResult[Person] = {
      for {
        age <- (json \ "age").validate[Int]
        name <- (json \ "name").validate[Name]
      } yield new Person(age, name)
    }

    def writes(person : Person) : JsValue = {
      Json.obj(("age", person.age), ("name", person.name))
    }
  }

}

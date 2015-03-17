import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest, WithApplication}

class PersonAPI2Spec extends Specification {

  "PersonAPI2#register" should {

    "register person" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/v2/person",
        FakeHeaders(Seq(CONTENT_TYPE -> Seq("application/json"))),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "last":"LastName"}}""")))
      status(result) mustEqual OK
      contentAsString(result) mustEqual """{"age":24,"name":{"first":"FirstName","last":"LastName"}}"""
    }

    "display json parse error caused by PersonJsonFormatter" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/v2/person",
        FakeHeaders(Seq(CONTENT_TYPE -> Seq("application/json"))),
        Json.parse( """{"typo!!!":24, "name":{"first":"FirstName", "last":"LastName"}}""")))
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual """{"obj.age":[{"msg":"error.path.missing","args":[]}]}"""
    }

    "display json parse error caused by NameJsonFormatter" in new WithApplication {
      val Some(result) = route(FakeRequest(POST, "/api/v2/person",
        FakeHeaders(Seq(CONTENT_TYPE -> Seq("application/json"))),
        Json.parse( """{"age":24, "name":{"first":"FirstName", "typo!!!":"LastName"}}""")))
      status(result) mustEqual BAD_REQUEST
      contentAsString(result) mustEqual """{"obj.name.last":[{"msg":"error.path.missing","args":[]}]}"""
    }
  }
}


import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.jdbc.Predef._

class GatlingTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .userAgentHeader("PostmanRuntime/7.1.1")

  val headers_2 = Map(
    "Content-Type" -> "application/json"
  )

  val search: ChainBuilder = exec(http("Home")
    .get("/hi"))

  val simplePost: ChainBuilder = exec(http("Post")
    .post("/hi")
    .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
    .body(StringBody("""{"name": "ads4"}""")))

  val repetition: ChainBuilder = repeat(10, "n") {
    exec(http("Hi ${n}")
        .post("/hi")
      .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
      .body(StringBody("""{"name": "ads${n}"}"""))
    ).pause(1)
  }

  val findAndDelete: ChainBuilder = repeat(10, "n") {
    exec(http("list")
      .get("/hi"))
      .pause(2)
      .exec(http("get${n}")
        .get("/hi/${n}"))
      .pause(3)
      .exec(http("delete${n}")
        .delete("/hi/${n}"))
      .pause(1)
  }

  val scn: ScenarioBuilder = scenario("Create Hi")
    .exec(http("list_his")
      .get("/hi"))
      .pause(1)
    .exec(http("list_his")
      .get("/hi"))

  val users: ScenarioBuilder = scenario("Empty").exec(search)
  val posts: ScenarioBuilder = scenario("Posts").exec(repetition)
  val deletes: ScenarioBuilder = scenario("Delete").exec(findAndDelete)

  setUp(
    scn.inject(rampUsers(10) over(10 seconds)),
    users.inject(rampUsers(10) over(10 seconds)),
    posts.inject(atOnceUsers(1)),
    deletes.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}
//https://gatling.io/docs/current/advanced_tutorial/
//https://gatling.io/docs/current/session/feeder/#feeder
//https://gatling.io/docs/current/http/http_check/#http-check
//https://gatling.io/docs/2.3/http/http_request/
//https://automationrhapsody.com/performance-testing-with-gatling-tips-and-tricks-for-advanced-usage/
//https://mwclearning.com/?p=1678

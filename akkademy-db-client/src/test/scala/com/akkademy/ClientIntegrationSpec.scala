package com.akkademy

import org.scalatest.{FunSpecLike, ShouldMatchers}
import scala.concurrent.Await
import scala.language.postfixOps

import scala.concurrent.duration._

class ClientIntegrationSpec extends FunSpecLike with ShouldMatchers {
  val client = new Client("""127.0.0.1:2552""")

  describe("akkademyDbClient") {
    it("should set a value") {
      client.set("123", new Integer(123))
      val futureResult = client.get("123")
      val result = Await.result(futureResult, 10 seconds)
      result should equal(123)
    }
  }
}

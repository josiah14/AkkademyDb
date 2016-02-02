package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.akkademy.messages.SetRequest
import org.scalatest.{FunSpecLike, ShouldMatchers}

import scala.concurrent.duration._

class AkkademyDbSpec extends FunSpecLike with ShouldMatchers {
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  describe("AkkademyDb") {
    describe("given SetRequest") {
      it("should place a key/value pair into the cache") {
        val actorRef = TestActorRef(new AkkademyDb, """akkademydb-actor""")
        actorRef ! SetRequest("key", "value")

        val akkademyDb = actorRef.underlyingActor
        akkademyDb.cache.get("key") should equal(Some("value"))
      }
    }
  }
}

package com.akkademy

import akka.actor.Actor
import akka.event.Logging
import com.akkademy.messages.SetRequest
import scala.collection.mutable.HashMap

class AkkademyDb extends Actor {
  val cache = new HashMap[String, String]
  val log = Logging(context.system, this)

  override def receive = {
    case SetRequest(key, value) => {
      log.info("received SetRequest - key: {} value: {}", key, value)
      cache.put(key, value)
    }
    case other => log.info("received unknown message: {}", other)
  }
}

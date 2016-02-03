package com.akkademy

import akka.actor.{Actor, ActorSystem, Status, Props}
import akka.event.Logging
import com.akkademy.messages.{KeyNotFoundException, GetRequest, SetRequest}
import scala.collection.mutable.HashMap

class AkkademyDb extends Actor {
  val cache = new HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive = {
    case SetRequest(key, value) =>
      log.info("received SetRequest - key: {} value: {}", key, value)
      cache.put(key, value)
      sender ! Status.Success
    case GetRequest(key) =>
      log.info("received GetRequest - key: {}", key)
      sender() ! cache.get(key).getOrElse(Status.Failure(new KeyNotFoundException(key)))
    case other => Status.Failure(new ClassNotFoundException)
  }
}

object Main extends App {
  val system = ActorSystem("akkademy")
  system.actorOf(Props[AkkademyDb], name = "akkademy-db")
}

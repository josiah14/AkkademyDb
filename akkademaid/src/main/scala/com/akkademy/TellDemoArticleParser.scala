// package com.akkademy

// import java.util.concurrent.TimeoutException

// import akka.actor.Status.Failure
// import akka.actor.{Actor, ActorRef, Props}
// import akka.util.Timeout

// import com.akkademy.mesasges.{GetRequest, SetRequest}

// class TellDemoArticleParser(cacheActorPath: String,
//                             httpClientActorPath: String,
//                             articleParserActorPath: String,
//                             implicit val timeout: Timeout) extends Actor {
//   val cacheActor = context.actorSelection(cacheActorPath)
// }

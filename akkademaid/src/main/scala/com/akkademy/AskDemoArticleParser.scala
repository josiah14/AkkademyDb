package com.akkademy

import akka.actor.Actor
import akka.pattern._
import akka.util.Timeout
import com.akkademy.messages.{SetRequest, GetRequest}

import scala.concurrent.{Future, Await}
import scala.util.{Success, Failure, Either, Left, Right}

class AskDemoArticleParser(cacheActorPath: String,
                           httpClientActorPath: String,
                           articleParserActorPath: String,
                           implicit val timeout: Timeout) extends Actor {
  val cacheActor = context.actorSelection(cacheActorPath)
  val httpClientActor = context.actorSelection(httpClientActorPath)
  val articleParserActor = Await.result(context.actorSelection(articleParserActorPath).resolveOne(), timeout.duration)

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Note there are 3 asks so this potentially ccreates 6 extra objects:
    * - 3 Promises
    * - 3 Extra actors
    * It's a bit simpler than the tell example.
    */
  override def receive: Receive = { case ParseArticle(uri) =>
    val senderRef = sender()

    val cacheResult: Future[Either[ArticleBody, String]] = (cacheActor ? GetRequest(uri)).mapTo[String] map(Right(_))

    val result: Future[Either[ArticleBody, String]] = (cacheResult recoverWith { case _ =>
      // the shortest solution I could come up with
      (httpClientActor ? uri).mapTo[HttpResponse].map(res => ParseHtmlArticle(uri, res.body))
        .pipeTo(articleParserActor).future.mapTo[ArticleBody].map(Left(_))

      /*** Below is an alternative using comprehensions ***/
      // for (
      //   httpResponse <- (httpClientActor ? uri).mapTo[HttpResponse];
      //   parsedArticle <- (articleParserActor ? ParseHtmlArticle(uri, httpResponse.body)).mapTo[ArticleBody]
      // ) yield Left(parsedArticle)
    }).mapTo[Either[ArticleBody, String]]

    result onComplete {
      case Success(article) =>
        article.fold(x => cacheActor ! SetRequest(uri, x.body), _ => println("cached result!"))
        article.fold(senderRef ! _, senderRef ! _)
      case Failure(e) => senderRef ! akka.actor.Status.Failure(e)
    }
  }
}

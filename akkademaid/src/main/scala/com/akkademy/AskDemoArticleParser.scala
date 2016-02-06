package com.akkademy

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import com.akkademy.messages.{SetRequest, GetRequest}

import scala.concurrent.Future
import scala.util.{Success, Failure, Either, Left, Right}

class AskDemoArticleParser(cacheActorPath: String,
                           httpClientActorPath: String,
                           articleParserActorPath: String,
                           implicit val timeout: Timeout) extends Actor {
  val cacheActor = context.actorSelection(cacheActorPath)
  val httpClientActor = context.actorSelection(httpClientActorPath)
  val articleParserActor = context.actorSelection(articleParserActorPath)

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Note there are 3 asks so this potentially ccreates 6 extra objects:
    * - 3 Promises
    * - 3 Extra actors
    * It's a bit simpler than the tell example.
    */
  override def receive: Receive = { case ParseArticle(uri) =>
    val senderRef = sender()

    val cacheResult: Future[Either[ArticleBody, String]] =
      cacheActor ? GetRequest(uri) map(x => Right(x.asInstanceOf[String]))

    val result: Future[Either[ArticleBody, String]] = (cacheResult recoverWith { case _ =>
      for (
        httpResponse <- (httpClientActor ? uri).mapTo[HttpResponse];
        parsedArticle <- (articleParserActor ? ParseHtmlArticle(uri, httpResponse.body)).mapTo[ArticleBody]
      ) yield Left(parsedArticle)
    }).mapTo[Either[ArticleBody, String]]

    result onComplete {
      case Success(article) =>
        article match {
          case Right(_) => println("cached result!")
          case Left(articleBody) => cacheActor ! SetRequest(uri, articleBody.body)
        }
        article.fold(senderRef ! _, senderRef ! _)
      case Failure(e) => senderRef ! akka.actor.Status.Failure(e)
    }
  }
}

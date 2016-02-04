package com.akkademy

import akka.actor.Actor
import de.l3s.boilerpipe.extractors.ArticleExtractor

class ParsingActor extends Actor {
  override def receive: Receive = {
    case ParseHtmlArticle(uri, html) =>
      sender() ! ArticleBody(uri, ArticleExtractor.INSTANCE.getText(html))
    case x => System.err.println("unknown message " + x.getClass)
  }
}

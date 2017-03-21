package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.DeDupFilter
import com.jh.paf.model.{DecryptedMessage, Message}

import scala.collection.immutable
import scala.concurrent.duration._

import language.postfixOps

class DeDupDecryptedMessage extends DeDupFilter[DecryptedMessage] {

  override def removeDuplicates[DecryptedMessage](messages: Seq[Message[DecryptedMessage]]): immutable.Seq[Message[DecryptedMessage]] = {
    val deduplicated = messages
      .map {
        message =>
          message.content -> message
      }
      .groupBy(_._1)
      .map {
        case (content, duplicatedMessages) =>
          duplicatedMessages.map(_._2).head
      }
      .toSeq

    scala.collection.immutable.Seq(deduplicated: _*)
  }
}

object DeDupFilterFlow {

  def apply[B](deduplicator: DeDupFilter[B]): Flow[Message[B], Message[B], NotUsed] =
    Flow[Message[B]]
      .groupedWithin(100, 1 seconds)
      .map {
        deduplicator.removeDuplicates
      }
      .mapConcat {
        identity
      }
      .buffer(10 * 100, OverflowStrategy.backpressure)

}

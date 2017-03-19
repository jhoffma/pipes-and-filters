package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Flow
import com.jh.paf.model.Message

import scala.concurrent.duration._

object DeDupFilterFlow {

  def apply[B](): Flow[Message[B], Message[B], NotUsed] =
    Flow[Message[B]]
      .groupedWithin(100, 5 seconds)
      .map {
        //RedundantNotificationsReduction()
      }
      .mapConcat {
        identity
      }
      .buffer(10 * 100, OverflowStrategy.backpressure)

}

package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.{AuthenticateFilter, DeDupFilter, DecryptFilter}
import com.jh.paf.model.Message

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

object PipesAndFiltersFlow {

  def apply[A, B](decryptor: DecryptFilter[A, B], authenticator: AuthenticateFilter[B],
                  deduplicator: DeDupFilter[B], maxAggregationWindow: Int, maxAggregationDuration: FiniteDuration)
                 (implicit ec : ExecutionContext):
  Flow[Message[A], Message[B], NotUsed] =
    Flow[Message[A]]
      .via(DecryptFilterFlow(decryptor))
      .via(AuthenticateFilterFlow(authenticator))
      .via(DeDupFilterFlow(deduplicator, maxAggregationWindow, maxAggregationDuration))

}

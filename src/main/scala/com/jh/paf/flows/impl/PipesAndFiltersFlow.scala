package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.{AuthenticateFilter, DecryptFilter}
import com.jh.paf.model.Message

object PipesAndFiltersFlow {

  def apply[A, B](decryptor: DecryptFilter[A, B], authenticator: AuthenticateFilter[B]): Flow[Message[A], Message[B], NotUsed] =
    Flow[Message[A]]
      .via(DecryptFilterFlow(decryptor))
      .via(AuthenticateFilterFlow(authenticator))

}

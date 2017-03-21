package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.AuthenticateFilter
import com.jh.paf.model.{DecryptedMessage, Message}

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

class AuthenticateDecryptedMessageFilter extends AuthenticateFilter[DecryptedMessage] {

  val validTokens = immutable.Seq("aaa", "bbb", "ccc")

  override def authenticate(msg: Message[DecryptedMessage]): Boolean = {
    validTokens.contains(msg.content.getAuthToken)
  }
}

object AuthenticateFilterFlow {

  def apply[B](authenticator: AuthenticateFilter[B]) (implicit ec : ExecutionContext) : Flow[Message[B], Message[B], NotUsed] =
    Flow[Message[B]]
    .mapAsync(10)(msg =>
      //Assume that 'authenticate' is a long lasting operation based on call to external service or DB
      Future((authenticator.authenticate(msg), msg))
    )
    .filter(_._1)
    .map(_._2)
}

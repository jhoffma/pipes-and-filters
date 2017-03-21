package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.AuthenticateFilter
import com.jh.paf.model.{DecryptedMessage, Message}

import scala.collection.immutable

class AuthenticateDecryptedMessageFilter extends AuthenticateFilter[DecryptedMessage] {

  val validTokens = immutable.Seq("aaa", "bbb", "ccc")

  override def authenticate(msg: Message[DecryptedMessage]): Boolean = {
    validTokens.contains(msg.content.getAuthToken)
  }
}

object AuthenticateFilterFlow {
  def apply[B](authenticator: AuthenticateFilter[B]): Flow[Message[B], Message[B], NotUsed] =
    Flow[Message[B]]
    .map {
      decryptedMessage =>
        (authenticator.authenticate(decryptedMessage), decryptedMessage)
    }
    .filter(_._1)
    .map(_._2)
}

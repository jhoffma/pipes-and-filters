package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.DecryptFilter
import com.jh.paf.model.{DecryptedMessage, EncryptedMessage, Message}


class DecryptEncryptedMessage extends DecryptFilter[EncryptedMessage, DecryptedMessage] {
  override def decrypt[EncryptedMessage]: (Message[EncryptedMessage]) => Message[DecryptedMessage] = {
    encMsg =>
      new DecryptedMessage("dupa")
  }
}

object DecryptFilterFlow {
  def apply[A, B](decryptor: DecryptFilter[A, B]): Flow[Message[A], Message[B], NotUsed] =
    Flow[Message[A]]
      .map { decryptor.decrypt }
}


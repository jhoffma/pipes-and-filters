package com.jh.paf.flows.impl

import akka.NotUsed
import akka.stream.scaladsl.Flow
import com.jh.paf.flows.DecryptFilter
import com.jh.paf.model.{DecryptedMessage, EncryptedMessage, Message}
import org.apache.commons.codec.binary.Base64

class DecryptEncryptedMessage extends DecryptFilter[EncryptedMessage, DecryptedMessage] {
  override def decrypt[A]: (Message[A]) => Message[DecryptedMessage] = {
    genericMsg =>
      genericMsg.content match {
        case encMsg : EncryptedMessage =>
          new DecryptedMessage(new String(Base64.decodeBase64(encMsg.content.getBody)), encMsg.content.getAuthToken)
      }
  }
}

object DecryptFilterFlow {
  def apply[A, B](decryptor: DecryptFilter[A, B]): Flow[Message[A], Message[B], NotUsed] =
    Flow[Message[A]]
      .map { decryptor.decrypt }
}
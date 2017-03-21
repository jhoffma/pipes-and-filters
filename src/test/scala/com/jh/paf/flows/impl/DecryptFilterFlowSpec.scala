package com.jh.paf.flows.impl

import akka.stream.scaladsl.{Sink, Source}
import com.jh.paf.model.{DecryptedMessage, EncryptedMessage}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class DecryptFilterFlowSpec extends BaseAkkaFlowSpec {

  behavior of "DecryptFilterFlow"

  it should "decrypt message" in {
    //given
    val encryptedMessage = new EncryptedMessage("c29tZUVuY3J5cHRlZFBheWxvYWQ=", "aaa")
    val flowUnderTest = DecryptFilterFlow(new DecryptEncryptedMessage)

    //when
    val future = Source.single(encryptedMessage).via(flowUnderTest).runWith(Sink.head)
    val result = Await.result(future, 3 seconds)

    //then
    result should be (new DecryptedMessage("someEncryptedPayload", "aaa"))
  }
}

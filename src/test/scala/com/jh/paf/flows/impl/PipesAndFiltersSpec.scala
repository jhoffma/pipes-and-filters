package com.jh.paf.flows.impl

import akka.stream.scaladsl.{Sink, Source}
import com.jh.paf.model.{DecryptedMessage, EncryptedMessage, Message}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class PipesAndFiltersSpec extends BaseAkkaFlowSpec {

  behavior of "end to end test scenario"

  it should "test entire flow form end to end" in {
    //given
    val flowUnderTest = PipesAndFiltersFlow(new DecryptEncryptedMessage, new AuthenticateDecryptedMessageFilter, new DeDupDecryptedMessage, 5, 2 seconds)
    val messages = messagesFixture()

    //when
    val future = Source.apply(messages).via(flowUnderTest).runWith(Sink.seq)
    val result = Await.result(future, 3 seconds)

    //then
    result.length should be(3)
    result should contain(new DecryptedMessage("someMsg", "aaa"))
    result should contain(new DecryptedMessage("alaMaKota", "bbb"))
    result should contain(new DecryptedMessage("another payload", "ccc"))
  }

  private def messagesFixture() : List[Message[EncryptedMessage]] = {
    List(new EncryptedMessage("c29tZU1zZw==", "aaa"), new EncryptedMessage("c29tZU1zZw==", "aaa"), new EncryptedMessage("c29tZU1zZw==", "aaa"),
      new EncryptedMessage("YWxhTWFLb3Rh", "bbb"), new EncryptedMessage("YW5vdGhlciBwYXlsb2Fk", "ccc"),
      new EncryptedMessage("dW5hdGhvcml6ZWQgbWVzc2FnZQ==", "ggg")
    )
  }

}

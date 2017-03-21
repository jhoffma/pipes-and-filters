package com.jh.paf.flows.impl

import akka.stream.scaladsl.{Sink, Source}
import com.jh.paf.model.DecryptedMessage

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class AuthenticateFilterFlowSpec extends BaseAkkaFlowSpec {

  behavior of "authenticate filter"

  it should "filter out not authenticated messages" in {
    //given
    val flowUnderTest = AuthenticateFilterFlow(new AuthenticateDecryptedMessageFilter)
    val firstMsg = new DecryptedMessage("first", "fdsfd")
    val secondMsg = new DecryptedMessage("second", "aaa")
    val thirdMsg = new DecryptedMessage("other message", "ccc")
    val fourthMsg = new DecryptedMessage("should not be authenticated", "ddd")
    val messages = List(firstMsg, secondMsg, thirdMsg, fourthMsg)

    //when
    val future = Source.apply(messages).via(flowUnderTest).runWith(Sink.seq)
    val result = Await.result(future, 3 seconds)

    //then
    result.length should be(2)
    result should contain(secondMsg)
    result should contain(thirdMsg)
  }

}

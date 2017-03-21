package com.jh.paf.flows.impl

import akka.stream.scaladsl.{Sink, Source}
import com.jh.paf.model.DecryptedMessage

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class DeDupFilterFlowSpec extends BaseAkkaFlowSpec {

  behavior of "DeDupFilterFlow"

  it should "remove duplicated messages" in {
    //given
    val flowUnderTest = DeDupFilterFlow(new DeDupDecryptedMessage)
    val firstMsg = new DecryptedMessage("duplicated", "aaa")
    val secondMsg = new DecryptedMessage("duplicated", "aaa")
    val thirdMsg = new DecryptedMessage("other message", "bbb")
    val fourthMsg = new DecryptedMessage("and another one", "ccc")
    val messages = List(firstMsg, secondMsg, thirdMsg, fourthMsg)

    //when
    val future = Source.apply(messages).via(flowUnderTest).runWith(Sink.seq)
    val result = Await.result(future, 3 seconds)

    //then
    result.length should be(3)
    result should contain(firstMsg)
    result should contain(thirdMsg)
    result should contain(fourthMsg)
  }

}

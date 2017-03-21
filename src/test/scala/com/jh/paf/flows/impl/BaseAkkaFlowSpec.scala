package com.jh.paf.flows.impl

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.{FlatSpec, Matchers}

abstract class BaseAkkaFlowSpec extends FlatSpec with Matchers {

  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = actorSystem.dispatcher

}

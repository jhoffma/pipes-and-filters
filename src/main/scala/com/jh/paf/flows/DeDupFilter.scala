package com.jh.paf.flows

import com.jh.paf.model.Message

import scala.collection.immutable

trait DeDupFilter[B] {

  def removeDuplicates[B](messages: Seq[Message[B]]) : immutable.Seq[Message[B]]

}

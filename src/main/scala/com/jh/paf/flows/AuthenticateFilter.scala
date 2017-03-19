package com.jh.paf.flows

import com.jh.paf.model.Message

trait AuthenticateFilter[A] {

  def authenticate(msg: Message[A]) : Boolean

}

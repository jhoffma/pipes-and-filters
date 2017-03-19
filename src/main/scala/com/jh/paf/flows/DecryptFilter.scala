package com.jh.paf.flows

import com.jh.paf.model.Message


trait DecryptFilter[A, B] {

  def decrypt[A] : Message[A] => Message[B]

}

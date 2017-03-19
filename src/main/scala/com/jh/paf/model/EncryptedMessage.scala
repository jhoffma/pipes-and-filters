package com.jh.paf.model

class EncryptedMessage(body: String) extends Message[EncryptedMessage] {

  override def content: EncryptedMessage = this

  def getBody() : String = body
}

package com.jh.paf.model

class DecryptedMessage(body: String) extends Message[DecryptedMessage] {

  override def content: DecryptedMessage = this

  def getBody() : String = body

  def getAuthToken() : String = "someAuthToken"
}

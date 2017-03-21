package com.jh.paf.model

class EncryptedMessage(body: String, authToken: String) extends Message[EncryptedMessage] {

  override def content: EncryptedMessage = this

  def getBody : String = body

  def getAuthToken : String = authToken

  override def equals(that: Any): Boolean =
    that match {
      case that: EncryptedMessage =>
        that.isInstanceOf[EncryptedMessage] && that.getBody == this.getBody && that.getAuthToken == this.getAuthToken
      case _ => false
    }

  override def toString: String = getBody
}

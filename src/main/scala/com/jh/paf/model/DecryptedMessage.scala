package com.jh.paf.model

class DecryptedMessage(body: String, authToken: String) extends Message[DecryptedMessage] {

  override def content: DecryptedMessage = this

  def getBody : String = body

  def getAuthToken : String = authToken

  override def equals(that: Any): Boolean =
    that match {
      case that: DecryptedMessage =>
        that.isInstanceOf[DecryptedMessage] && that.getBody == this.getBody && that.getAuthToken == this.getAuthToken
      case _ => false
    }

  override def toString: String = getBody
}

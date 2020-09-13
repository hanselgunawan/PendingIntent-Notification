package com.hanseltritama.pendingintentnotification

class Message(private val text: CharSequence, private val sender: CharSequence?) {

    var mText: CharSequence = ""
    private var timestamp: Long = 0
    var mSender: CharSequence = ""

    init {
        mText = this.text
        mSender = this.sender ?: "Me"
        timestamp = System.currentTimeMillis()
    }

    fun getText(): CharSequence {
        return mText
    }

    fun getTimestamp(): Long {
        return timestamp
    }

    fun getSender(): CharSequence {
        return mSender
    }

}
package com.sample.myplayer.util


open class ServerException(
    override val message: String? = null,
    open val reason: String = "",
    override val cause: Throwable? = null
) : Exception(message, cause) {

    override fun toString(): String {
        return "ServerException(reason='$reason', message='$message')"
    }
}

class ServerError(override val reason: String, override val message: String) : ServerException()

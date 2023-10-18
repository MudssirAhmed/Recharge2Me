package com.recharge2mePlay.recharge2me.utils

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
        @SerializedName("url")
        val url: String = "",
        @SerializedName("error")
        val error: String = "",
        @SerializedName("message")
        val message: String = "",
        @SerializedName("reason")
        val reason: String = "",
        @SerializedName("timestamp")
        val timestamp: String = "",
        @SerializedName("status")
        val code: Int = 0,
        val data: Data? = null
) {
    override fun toString(): String {
        return "ErrorResponse(url='$url', error='$error', message='$message', reason='$reason', timestamp='$timestamp', code=$code, data=$data)"
    }
}

data class Data(
        val groupId: String? = null
) {
    override fun toString(): String {
        return "Data(groupId=$groupId)"
    }
}
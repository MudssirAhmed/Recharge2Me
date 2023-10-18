package com.recharge2mePlay.recharge2me.utils

import com.google.gson.Gson
import okhttp3.ResponseBody

object FunctionUtils {
    fun parseError(responseBody: ResponseBody?) :String{
        try {
            val errorResponse: ErrorResponse = Gson().fromJson(
                    responseBody?.charStream(),
                    ErrorResponse::class.java
            )
            return when (errorResponse.code) {
                500 -> {
                    errorResponse.message
                }
                401 -> {
                    errorResponse.message
                }
                404 -> {
                    errorResponse.message
                }
                427 -> {
                    errorResponse.message
                }
                429 -> {
                    "Api rate limit exceeded. Please try later"
                }
                else -> errorResponse.message
            }
        } catch (e:Exception) {
            return e.message?: "Please try later"
        }
    }
}
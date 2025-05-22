package com.silentcid.homemind.core.retrofit.utils

class ApiException(
    val code: Int,
    message: String,
    val responseBody: String,
) : RuntimeException(message)
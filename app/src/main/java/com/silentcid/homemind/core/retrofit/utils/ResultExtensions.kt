package com.silentcid.homemind.core.retrofit.utils

import com.silentcid.homemind.R
import com.silentcid.homemind.domain.models.UserFacingError
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException



fun HttpException.toApiException() = ApiException(code(), message(), response()?.errorBody().toString())

fun <T> Result<T>.mapErrors(): Result<T> {
    return when (val exception = exceptionOrNull()) {
        is HttpException -> Result.failure(exception.toApiException())
        else -> this
    }
}

fun <T> Result<T>.logFailure(): Result<T> = apply {
    exceptionOrNull()?.let { e ->
        println("❌ Error: ${e.message}") // or Timber.d(...) if using logging
    }
}

fun <T> Result<T>.handleFailure(
    errorState: MutableStateFlow<UserFacingError>,
    fallback: UserFacingError = UserFacingError.GeneralError(
        title = R.string.general_error_title,
        description = R.string.general_error_description
    )
): Result<T> = apply {
    exceptionOrNull()?.let { throwable ->
        val mapped = when (throwable) {
            is ApiException -> UserFacingError.ApiError(
                code = throwable.code,
                title = R.string.api_error_title,
                description = R.string.api_error_description
            )
            is HttpException -> UserFacingError.ApiError(
                code = throwable.code(),
                title = R.string.network_error_title,
                description = R.string.network_error_description
            )
            else -> fallback
        }
        errorState.value = mapped
    }
}

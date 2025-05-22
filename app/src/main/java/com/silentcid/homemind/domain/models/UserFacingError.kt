package com.silentcid.homemind.domain.models

import androidx.annotation.StringRes

// Marker interface for all domain-level models (optional)
interface DomainModel

sealed class UserFacingError : DomainModel {

    data class GeneralError(
        @StringRes val title: Int,
        @StringRes val description: Int
    ) : UserFacingError()

    data class ApiError(
        val code: Int = 0,
        @StringRes val title: Int,
        @StringRes val description: Int
    ) : UserFacingError()

    object NoError : UserFacingError()
}

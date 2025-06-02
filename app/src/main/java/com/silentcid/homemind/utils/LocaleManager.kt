package com.silentcid.homemind.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleManager {


    const val DEFAULT_LOCALE_INDEX = 0 // Users default locale
    const val ENGLISH = "en"
    const val JAPANESE = "ja"

    fun setLocale(context: Context, language: String): Context {

        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}
package com.silentcid.homemind.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleManager {
    const val DEFAULT_LOCALE_INDEX = 0
    const val ENGLISH = "en"
    const val JAPANESE = "ja"

    /**
     * Updates the locale for the given context (usually an Activity).
     * This method uses updateConfiguration to ensure that even components 
     * in separate windows (like Dialogs and BottomSheets) that might 
     * reference the Activity's resources directly will see the localized strings.
     */
    @Suppress("DEPRECATION")
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale.forLanguageTag(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        // Update the current context's resources directly. 
        // This affects the Activity if 'context' is the Activity.
        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Also update application resources to ensure consistency across the app
        val appResources = context.applicationContext.resources
        appResources.updateConfiguration(configuration, appResources.displayMetrics)

        // We return the original context (the Activity) to ensure Hilt 
        // can find the ActivityComponent without any unwrapping issues.
        return context
    }
}

package com.silentcid.homemind.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentcid.homemind.data.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val language: StateFlow<String?> = preferenceManager.languageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val theme: StateFlow<String> = preferenceManager.themeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "system")

    fun setLanguage(language: String) {
        viewModelScope.launch {
            preferenceManager.updateLanguage(language)
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch {
            preferenceManager.updateTheme(theme)
        }
    }
}

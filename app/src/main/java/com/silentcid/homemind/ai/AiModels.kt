package com.silentcid.homemind.ai

sealed class GemmaResult {
    data class Success(val response: String) : GemmaResult()
    data class Error(val message: String, val exception: Throwable? = null) : GemmaResult()
    data object Loading : GemmaResult()
    data object NotInitialized : GemmaResult()
}

data class DownloadState(
    val isDownloading: Boolean = false,
    val progress: Int = 0,
    val status: String = ""
)

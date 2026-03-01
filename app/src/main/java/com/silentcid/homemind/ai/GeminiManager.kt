package com.silentcid.homemind.ai

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.silentcid.homemind.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiManager @Inject constructor() {
    companion object {
        private const val TAG = "GeminiManager"
        private const val MODEL_NAME = "gemini-3.1-flash-image-preview"
    }

    private val generativeModel = GenerativeModel(
        modelName = MODEL_NAME,
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val _isModelLoadedFlow = MutableStateFlow(true)
    val isModelLoadedFlow: StateFlow<Boolean> = _isModelLoadedFlow.asStateFlow()

    private val _downloadState = MutableStateFlow(DownloadState())
    val downloadState: StateFlow<DownloadState> = _downloadState.asStateFlow()

    suspend fun initialize(): GemmaResult = GemmaResult.Success("Gemini Ready")

    suspend fun loadModelFromUri(uri: Uri): GemmaResult = GemmaResult.Success("Cloud model in use")

    suspend fun generateContent(prompt: String): GemmaResult = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(prompt)
            val text = response.text
            if (text != null) {
                GemmaResult.Success(text)
            } else {
                GemmaResult.Error("Empty response from Gemini")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini generation failed", e)
            GemmaResult.Error("Gemini error: ${e.message}", e)
        }
    }

    suspend fun analyzeImage(bitmap: Bitmap, prompt: String): GemmaResult = withContext(Dispatchers.IO) {
        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = generativeModel.generateContent(inputContent)
            val text = response.text
            if (text != null) {
                GemmaResult.Success(text)
            } else {
                GemmaResult.Error("Empty response from Gemini")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini image analysis failed", e)
            GemmaResult.Error("Gemini error: ${e.message}", e)
        }
    }

    suspend fun analyzeImageForProduct(bitmap: Bitmap?, language: String): GemmaResult {
        if (bitmap == null) return GemmaResult.Error("No image provided")
        val prompt = if (language == "ja") {
            "この画像の商品を特定してください。\n以下の形式のみで回答してください：\n検出された製品: [商品名]\n数量: 1\nカテゴリー: [dairy/fruits/bakery/meat/vegetables/beverages/snacks/toiletries/sauces/other]"
        } else {
            "Identify the product in this image.\nRespond ONLY in this exact format:\nDetected: [product name]\nQuantity: 1\nCategory: [dairy/fruits/bakery/meat/vegetables/beverages/snacks/toiletries/sauces/other]"
        }
        return analyzeImage(bitmap, prompt)
    }

    suspend fun generateGrocerySuggestions(lang: String): GemmaResult {
        val prompt = if (lang == "ja") {
            "おすすめの食料品を5つ提案してください。\n各商品は以下の形式で個別に記述してください：\n[商品名]: [なぜそれが必要か、または提案の理由]"
        } else {
            "Suggest 5 interesting grocery items to buy.\nFormat each item exactly like this:\n[Product Name]: [Brief reason why it's suggested]"
        }
        return generateContent(prompt)
    }

    suspend fun generateHouseholdSuggestions(lang: String): GemmaResult {
        val prompt = if (lang == "ja") {
            "今日の家事やタスクを5つ提案してください。\n各タスクは以下の形式で個別に記述してください：\n[タスク名]: [タスクの詳細な説明]"
        } else {
            "Suggest 5 practical household tasks for today.\nFormat each task exactly like this:\n[Task Name]: [Brief description of what to do]"
        }
        return generateContent(prompt)
    }

    suspend fun generateMealSuggestions(lang: String): GemmaResult {
        val prompt = if (lang == "ja") {
            "今日作る料理を3つ提案してください。健康的で新鮮なアイデアをお願いします。\n以下の形式を厳密に守ってください：\n\n料理名: [料理の名前]\nIngredients: [食材1, 食材2...]\nDirections: [簡単な調理手順]\n\n(各料理の間に空行を1つ入れてください)"
        } else {
            "Suggest 3 fresh and healthy meal ideas to cook today.\nUse this EXACT format for each meal:\n\nMeal: [Meal Name]\nIngredients: [Ingredient 1, Ingredient 2...]\nDirections: [Brief cooking steps]\n\n(Separate each meal block with a blank line)"
        }
        return generateContent(prompt)
    }

    fun release() {
        // No-op for Gemini
    }
}

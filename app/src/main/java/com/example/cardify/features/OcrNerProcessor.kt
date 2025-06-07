package com.example.cardify.features

import android.content.Context
import android.graphics.Bitmap
import com.example.cardify.models.BusinessCard
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.coroutines.tasks.await

class OcrNerProcessor(context: Context) {
    private val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    suspend fun process(bitmap: Bitmap): BusinessCard {
        val image = InputImage.fromBitmap(bitmap, 0)
        val result = recognizer.process(image).await()
        val text = result.text

        val nameRegex = Regex("[가-힣]{2,4}")
        val phoneRegex = Regex("\d{2,3}-\d{3,4}-\d{4}")
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        val positionRegex = Regex("(대표|이사|사원|팀장|매니저|과장|차장|부장)")

        val name = nameRegex.find(text)?.value ?: ""
        val phone = phoneRegex.find(text)?.value ?: ""
        val email = emailRegex.find(text)?.value ?: ""
        val position = positionRegex.find(text)?.value ?: ""

        val lines = text.lines()
        val company = lines.firstOrNull() ?: ""

        return BusinessCard(
            name = name,
            company = company,
            position = position,
            phone = phone,
            email = email,
            imageUrl = ""
        )
    }
}

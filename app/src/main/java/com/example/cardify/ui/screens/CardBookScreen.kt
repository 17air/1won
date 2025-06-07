package com.example.cardify.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cardify.models.BusinessCard

@Composable
fun CardBookScreen(cards: List<BusinessCard>) {
    LazyColumn {
        items(cards) { card ->
            Text(text = "${'$'}{card.name} - ${'$'}{card.company}")
        }
    }
}

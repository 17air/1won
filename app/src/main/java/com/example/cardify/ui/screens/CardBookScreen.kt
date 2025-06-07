package com.example.cardify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cardify.data.InMemoryCardRepository
import com.example.cardify.data.TempImageHolder
import com.example.cardify.models.BusinessCard

@Composable
fun CardBookScreen(navController: NavController) {
    val cards = InMemoryCardRepository.getCards()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        itemsIndexed(cards) { index, card ->
            CardRow(card = card, onEdit = {
                navController.navigate("edit_card/$index")
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CardRow(card: BusinessCard, onEdit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder for image
        Box(modifier = Modifier.size(64.dp).padding(end = 8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(card.name)
            Text(card.phone)
        }
        Button(onClick = onEdit) { Text("수정") }
    }
}

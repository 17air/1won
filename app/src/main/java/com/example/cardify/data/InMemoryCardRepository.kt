package com.example.cardify.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.cardify.models.BusinessCard

object InMemoryCardRepository {
    private val cards = mutableStateListOf<BusinessCard>()
    fun getCards(): SnapshotStateList<BusinessCard> = cards
    fun addCard(card: BusinessCard) { cards.add(card) }
    fun updateCard(index: Int, card: BusinessCard) {
        if (index in cards.indices) cards[index] = card
    }
}

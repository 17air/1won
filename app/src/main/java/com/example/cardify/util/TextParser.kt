package com.example.cardify.util

import com.example.cardify.models.BusinessCard

object TextParser {
    fun parse(lines: List<String>): BusinessCard {
        var name = ""
        var company = ""
        var position = ""
        var phone = ""
        var email = ""
        var sns = ""

        for (line in lines) {
            val trimmed = line.trim()
            when {
                email.isEmpty() && trimmed.contains("@") -> email = trimmed
                phone.isEmpty() && trimmed.replace("[^0-9]".toRegex(), "").length >= 8 -> phone = trimmed
                name.isEmpty() -> name = trimmed
                company.isEmpty() -> company = trimmed
                else -> if (position.isEmpty()) position = trimmed
            }
        }

        return BusinessCard(
            cardId = System.currentTimeMillis().toString(),
            name = name,
            company = company,
            position = position,
            phone = phone,
            email = email,
            sns = sns,
            imageUrl = ""
        )
    }
}

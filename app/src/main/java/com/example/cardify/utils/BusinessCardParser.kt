package com.example.cardify.utils

/**
 * Simple parser to extract contact information from raw text without using NER.
 */
object BusinessCardParser {
    data class ParsedCard(
        val name: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val company: String? = null
    )

    private val phoneRegex = Regex("""\+?\d[\d\s-]{7,}\d""")
    private val emailRegex = Regex("""[\w._%+-]+@[\w.-]+\.[A-Za-z]{2,}""")
    private val companyRegex = Regex("""(Inc|Corp|LLC|Co\.|Ltd|회사|주식회사)""", RegexOption.IGNORE_CASE)

    fun parse(text: String): ParsedCard {
        var name: String? = null
        var phone: String? = null
        var email: String? = null
        var company: String? = null

        text.lines().forEach { line ->
            val trimmed = line.trim()
            if (email == null) {
                emailRegex.find(trimmed)?.let { email = it.value }
            }
            if (phone == null) {
                phoneRegex.find(trimmed)?.let { phone = it.value }
            }
            if (company == null && companyRegex.containsMatchIn(trimmed)) {
                company = trimmed
            }
            if (name == null && trimmed.isNotEmpty() && trimmed.none { it.isDigit() }) {
                if (trimmed.split(" ").size <= 3) {
                    name = trimmed
                }
            }
        }

        return ParsedCard(
            name = name,
            phone = phone,
            email = email,
            company = company
        )
    }
}

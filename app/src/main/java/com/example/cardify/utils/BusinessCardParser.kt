package com.example.cardify.utils

/**
 * Simple parser to extract contact information from raw text without using NER.
 */
object BusinessCardParser {
    data class ParsedCard(
        val name: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val company: String? = null,
        val title: String? = null,
        val extra: String? = null
    )

    private val phoneRegex = Regex("""\+?\d[\d\s-]{7,}\d""")
    private val emailRegex = Regex("""[\w._%+-]+@[\w.-]+\.[A-Za-z]{2,}""")
    private val companyRegex = Regex("""(Inc|Corp|LLC|Co\.|Ltd|회사|주식회사)""", RegexOption.IGNORE_CASE)
    private val titleKeywords = listOf(
        "대표", "사장", "팀장", "이사", "과장", "차장", "실장", "본부장",
        "CEO", "CTO", "COO"
    )

    fun parse(text: String): ParsedCard {
        var name: String? = null
        var phone: String? = null
        var email: String? = null
        var company: String? = null
        var title: String? = null
        val extraLines = mutableListOf<String>()

        text.lines().forEach { line ->
            val trimmed = line.trim()
            var matched = false
            if (email == null) {
                emailRegex.find(trimmed)?.let { email = it.value; matched = true }
            }
            if (!matched && phone == null) {
                phoneRegex.find(trimmed)?.let { phone = it.value; matched = true }
            }
            if (!matched && company == null && companyRegex.containsMatchIn(trimmed)) {
                company = trimmed
                matched = true
            }
            if (!matched && title == null && titleKeywords.any { trimmed.contains(it, ignoreCase = true) }) {
                title = trimmed
                matched = true
            }
            if (!matched && name == null && trimmed.isNotEmpty() && trimmed.none { it.isDigit() }) {
                if (trimmed.split(" ").size <= 3) {
                    name = trimmed
                    matched = true
                }
            }
            if (!matched && trimmed.isNotEmpty()) {
                extraLines.add(trimmed)
            }
        }

        return ParsedCard(
            name = name,
            phone = phone,
            email = email,
            company = company,
            title = title,
            extra = extraLines.joinToString("\n").takeIf { it.isNotBlank() }
        )
    }
}

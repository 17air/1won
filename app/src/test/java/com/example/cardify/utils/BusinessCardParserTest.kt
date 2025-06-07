package com.example.cardify.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class BusinessCardParserTest {
    @Test
    fun parse_basicInfo() {
        val text = """
            홍길동
            주식회사 카드파이
            팀장
            +82 10-1234-5678
            gil@example.com
            기타 메모
        """.trimIndent()

        val parsed = BusinessCardParser.parse(text)
        assertEquals("홍길동", parsed.name)
        assertEquals("+82 10-1234-5678", parsed.phone)
        assertEquals("gil@example.com", parsed.email)
        assertEquals("주식회사 카드파이", parsed.company)
        assertEquals("팀장", parsed.title)
        assertEquals("기타 메모", parsed.extra)
    }
}

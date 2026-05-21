package dev.marcosfarias.pokedex.unittest

import dev.marcosfarias.pokedex.utils.ListStringConverter
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@Epic("Unit tests")
@Feature("ListStringConverter")
class ListStringConverterTest {

    private val converter = ListStringConverter()

    @Test
    fun `Check round Trip preserves Order And Values`() {
        val original = listOf("grass", "poison", "flying")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun `Check empty List round Trip`() {
        val original = emptyList<String>()
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun `Check single Element round Trip`() {
        val original = listOf("pikachu")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun `Check special Characters round Trip`() {
        val original = listOf("a b", "\"quoted\"", "unicode: печать", "tab\there")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun `Check from string empty Json Array returns Empty List`() {
        val result = converter.fromString("[]")
        assertTrue(result.isEmpty())
    }
}

package dev.marcosfarias.pokedex.unittest

import dev.marcosfarias.pokedex.utils.ListStringConverter
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@Epic("Unit")
@Feature("ListStringConverter")
class ListStringConverterTest {

    private val converter = ListStringConverter()

    @Test
    fun roundTrip_preservesOrderAndValues() {
        val original = listOf("grass", "poison", "flying")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun emptyList_roundTrip() {
        val original = emptyList<String>()
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun singleElement_roundTrip() {
        val original = listOf("pikachu")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun specialCharacters_roundTrip() {
        val original = listOf("a b", "\"quoted\"", "unicode: печать", "tab\there")
        val json = converter.fromList(original)
        assertEquals(original, converter.fromString(json))
    }

    @Test
    fun fromString_emptyJsonArray_returnsEmptyList() {
        val result = converter.fromString("[]")
        assertTrue(result.isEmpty())
    }
}

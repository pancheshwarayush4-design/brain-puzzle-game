package com.example

import com.example.data.DailyPuzzleGenerator
import com.example.data.LevelLibrary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `verify level library contains 25 unique handcrafted levels`() {
        assertEquals(25, LevelLibrary.levels.size)
        val level1 = LevelLibrary.getLevel(0)
        assertEquals("Which one is the BIGGEST?", level1.question)
        assertEquals(3, level1.hints.size)

        // Verify each level has unique id, question, hints, and explanation
        val ids = LevelLibrary.levels.map { it.id }.toSet()
        assertEquals(25, ids.size)
        LevelLibrary.levels.forEach { level ->
            assertTrue(level.title.isNotEmpty())
            assertTrue(level.question.isNotEmpty())
            assertTrue(level.hints.size >= 3)
            assertTrue(level.explanation.isNotEmpty())
        }
    }

    @Test
    fun `verify daily puzzle generator produces valid challenges`() {
        val daily = DailyPuzzleGenerator.getDailyPuzzle()
        assertNotNull(daily)
        assertTrue(daily.question.isNotEmpty())
        assertTrue(daily.extraOptions.isNotEmpty())

        val level100 = DailyPuzzleGenerator.getGeneratedLevel(100)
        assertNotNull(level100)
        assertEquals(100, level100.levelNumber)
    }
}

package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.DailyPuzzleGenerator
import com.example.data.GameRepository
import com.example.data.LevelLibrary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Playfinity Brain Puzzles", appName)
    }

    @Test
    fun `verify level library contains 25 core levels`() {
        assertEquals(25, LevelLibrary.levels.size)
        val level1 = LevelLibrary.getLevel(0)
        assertEquals("Which one is the BIGGEST?", level1.question)
        assertEquals(3, level1.hints.size)
    }

    @Test
    fun `verify game repository persistence and achievements`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = GameRepository(context)
        repository.resetAllProgress()
        assertEquals(5, repository.hintsCount)
        assertEquals(80, repository.brainIq)

        repository.setLevelStars(1, 3)
        assertEquals(3, repository.getLevelStars(1))

        val achievements = repository.getAchievements()
        assertTrue(achievements.isNotEmpty())
    }

    @Test
    fun `verify daily puzzle generator produces valid puzzle`() {
        val daily = DailyPuzzleGenerator.getDailyPuzzle()
        assertNotNull(daily)
        assertTrue(daily.question.isNotEmpty())
        assertTrue(daily.hints.isNotEmpty())
    }
}

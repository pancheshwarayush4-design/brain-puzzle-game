package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.Achievement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("playfinity_brain_puzzles_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_CURRENT_LEVEL = "key_current_level"
        private const val KEY_HINTS_COUNT = "key_hints_count"
        private const val KEY_BRAIN_IQ = "key_brain_iq"
        private const val KEY_LAST_DAILY_DATE = "key_last_daily_date"
        private const val KEY_DAILY_STREAK = "key_daily_streak"
        private const val KEY_SOUND_FX = "key_sound_fx"
        private const val KEY_MUSIC = "key_music"
        private const val KEY_HAPTICS = "key_haptics"
        private const val KEY_TOTAL_SOLVED = "key_total_solved"
    }

    var currentLevelIndex: Int
        get() = prefs.getInt(KEY_CURRENT_LEVEL, 0)
        set(value) = prefs.edit().putInt(KEY_CURRENT_LEVEL, value).apply()

    var hintsCount: Int
        get() = prefs.getInt(KEY_HINTS_COUNT, 5)
        set(value) = prefs.edit().putInt(KEY_HINTS_COUNT, value.coerceAtLeast(0)).apply()

    var brainIq: Int
        get() = prefs.getInt(KEY_BRAIN_IQ, 80)
        set(value) = prefs.edit().putInt(KEY_BRAIN_IQ, value).apply()

    var soundFxEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND_FX, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND_FX, value).apply()

    var musicEnabled: Boolean
        get() = prefs.getBoolean(KEY_MUSIC, true)
        set(value) = prefs.edit().putBoolean(KEY_MUSIC, value).apply()

    var hapticsEnabled: Boolean
        get() = prefs.getBoolean(KEY_HAPTICS, true)
        set(value) = prefs.edit().putBoolean(KEY_HAPTICS, value).apply()

    var totalSolved: Int
        get() = prefs.getInt(KEY_TOTAL_SOLVED, 0)
        set(value) = prefs.edit().putInt(KEY_TOTAL_SOLVED, value).apply()

    fun getLevelStars(levelId: Int): Int {
        return prefs.getInt("level_stars_$levelId", 0)
    }

    fun setLevelStars(levelId: Int, stars: Int) {
        val existing = getLevelStars(levelId)
        if (stars > existing) {
            prefs.edit().putInt("level_stars_$levelId", stars).apply()
        }
    }

    fun isLevelUnlocked(levelIndex: Int): Boolean {
        if (levelIndex == 0) return true
        val prevLevelId = levelIndex // since Level 1 has id 1
        return getLevelStars(prevLevelId) > 0 || levelIndex <= currentLevelIndex
    }

    fun isDailyCompletedToday(): Boolean {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        return prefs.getString(KEY_LAST_DAILY_DATE, "") == today
    }

    fun markDailyCompleted() {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val streak = getDailyStreak() + 1
        prefs.edit()
            .putString(KEY_LAST_DAILY_DATE, today)
            .putInt(KEY_DAILY_STREAK, streak)
            .apply()
        hintsCount += 2
        brainIq += 15
    }

    fun getDailyStreak(): Int {
        return prefs.getInt(KEY_DAILY_STREAK, 0)
    }

    fun getAchievements(): List<Achievement> {
        val solved = totalSolved
        val stars = (1..50).sumOf { getLevelStars(it) }
        val hintsUsed = prefs.getInt("hints_used_count", 0)
        val streak = getDailyStreak()

        return listOf(
            Achievement("first_step", "Doodle Rookie", "Solve your first brain puzzle", "✏️", solved >= 1, solved.coerceAtMost(1), 1),
            Achievement("thinker_5", "Curious Mind", "Solve 5 brain puzzles", "💡", solved >= 5, solved.coerceAtMost(5), 5),
            Achievement("brainiac_15", "Galaxy Brain", "Solve 15 brain puzzles", "🧠", solved >= 15, solved.coerceAtMost(15), 15),
            Achievement("master_25", "Playfinity Legend", "Solve all 25 core levels", "🏆", solved >= 25, solved.coerceAtMost(25), 25),
            Achievement("star_collector_20", "Constellation", "Collect 20 gold stars", "⭐", stars >= 20, stars.coerceAtMost(20), 20),
            Achievement("star_collector_50", "Supernova", "Collect 50 gold stars", "🌟", stars >= 50, stars.coerceAtMost(50), 50),
            Achievement("daily_streak_3", "Streak Master", "Complete 3 daily puzzles", "🔥", streak >= 3, streak.coerceAtMost(3), 3),
            Achievement("iq_120", "Mensa Candidate", "Reach 120 Brain IQ", "🎓", brainIq >= 120, brainIq.coerceAtMost(120), 120),
            Achievement("iq_150", "Einstein's Heir", "Reach 150 Brain IQ", "🚀", brainIq >= 150, brainIq.coerceAtMost(150), 150),
            Achievement("no_hints_5", "Independent Thinker", "Solve 5 levels with 3 stars", "🧐", (1..25).count { getLevelStars(it) == 3 } >= 5, (1..25).count { getLevelStars(it) == 3 }.coerceAtMost(5), 5),
            Achievement("eraser_rub", "Clean Slate", "Erase grandpa's beard", "✨", getLevelStars(9) > 0, if (getLevelStars(9) > 0) 1 else 0, 1),
            Achievement("vault_breaker", "Safe Cracker", "Unlock the Brain Vault", "🗝️", getLevelStars(25) > 0, if (getLevelStars(25) > 0) 1 else 0, 1)
        )
    }

    fun incrementHintsUsed() {
        val count = prefs.getInt("hints_used_count", 0) + 1
        prefs.edit().putInt("hints_used_count", count).apply()
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        hintsCount = 5
        brainIq = 80
        soundFxEnabled = true
        musicEnabled = true
        hapticsEnabled = true
    }
}

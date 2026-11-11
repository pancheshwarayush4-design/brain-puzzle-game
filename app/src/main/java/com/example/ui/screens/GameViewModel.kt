package com.example.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.DailyPuzzleGenerator
import com.example.data.GameRepository
import com.example.data.LevelLibrary
import com.example.model.Achievement
import com.example.model.PuzzleLevel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val currentLevel: PuzzleLevel,
    val currentLevelIndex: Int,
    val totalLevels: Int,
    val hintsCount: Int,
    val brainIq: Int,
    val starsForCurrentLevel: Int,
    val showVictoryModal: Boolean = false,
    val showWrongAttemptOverlay: Boolean = false,
    val wrongAttemptMessage: String = "",
    val showHintDialog: Boolean = false,
    val showLevelSelectDialog: Boolean = false,
    val showSettingsDialog: Boolean = false,
    val showAchievementsDialog: Boolean = false,
    val showDailyChallengeModal: Boolean = false,
    val achievements: List<Achievement> = emptyList(),
    val isDailyCompletedToday: Boolean = false,
    val dailyStreak: Int = 0
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val repository = GameRepository(application)
    val soundManager = SoundManager(application)

    private val _uiState = MutableStateFlow(
        createInitialState()
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        soundManager.soundEffectsEnabled = repository.soundFxEnabled
        soundManager.musicEnabled = repository.musicEnabled
        soundManager.hapticsEnabled = repository.hapticsEnabled
        if (repository.musicEnabled) {
            soundManager.startBackgroundMusic()
        }
    }

    private fun createInitialState(): GameUiState {
        val levelIdx = repository.currentLevelIndex.coerceIn(0, LevelLibrary.levels.lastIndex)
        val level = LevelLibrary.getLevel(levelIdx)
        return GameUiState(
            currentLevel = level,
            currentLevelIndex = levelIdx,
            totalLevels = LevelLibrary.levels.size,
            hintsCount = repository.hintsCount,
            brainIq = repository.brainIq,
            starsForCurrentLevel = repository.getLevelStars(level.id),
            achievements = repository.getAchievements(),
            isDailyCompletedToday = repository.isDailyCompletedToday(),
            dailyStreak = repository.getDailyStreak()
        )
    }

    fun onLevelSolved() {
        val level = _uiState.value.currentLevel
        val stars = 3 // Standard complete with full stars
        repository.setLevelStars(level.id, stars)
        repository.totalSolved += 1
        repository.brainIq += 5

        _uiState.update {
            it.copy(
                showVictoryModal = true,
                starsForCurrentLevel = stars,
                brainIq = repository.brainIq,
                achievements = repository.getAchievements()
            )
        }
    }

    fun onWrongAttempt(message: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    showWrongAttemptOverlay = true,
                    wrongAttemptMessage = message
                )
            }
            delay(1200L)
            _uiState.update {
                it.copy(showWrongAttemptOverlay = false)
            }
        }
    }

    fun nextLevel() {
        val nextIdx = (_uiState.value.currentLevelIndex + 1)
        if (nextIdx < LevelLibrary.levels.size) {
            loadLevel(nextIdx)
        } else {
            // Endless daily / procedural level
            val generated = DailyPuzzleGenerator.getGeneratedLevel(nextIdx + 1)
            _uiState.update {
                it.copy(
                    currentLevel = generated,
                    currentLevelIndex = nextIdx,
                    showVictoryModal = false,
                    starsForCurrentLevel = repository.getLevelStars(generated.id)
                )
            }
        }
    }

    fun replayLevel() {
        _uiState.update {
            it.copy(showVictoryModal = false)
        }
    }

    fun loadLevel(index: Int) {
        val level = LevelLibrary.getLevel(index)
        repository.currentLevelIndex = index
        _uiState.update {
            it.copy(
                currentLevel = level,
                currentLevelIndex = index,
                showVictoryModal = false,
                starsForCurrentLevel = repository.getLevelStars(level.id)
            )
        }
    }

    fun openHintDialog() {
        soundManager.playClick()
        _uiState.update { it.copy(showHintDialog = true) }
    }

    fun dismissHintDialog() {
        soundManager.playClick()
        _uiState.update { it.copy(showHintDialog = false) }
    }

    fun useHint() {
        if (repository.hintsCount > 0) {
            repository.hintsCount -= 1
            repository.incrementHintsUsed()
            soundManager.playSparkle()
            _uiState.update { it.copy(hintsCount = repository.hintsCount) }
        }
    }

    fun openLevelSelect() {
        soundManager.playClick()
        _uiState.update { it.copy(showLevelSelectDialog = true) }
    }

    fun dismissLevelSelect() {
        soundManager.playClick()
        _uiState.update { it.copy(showLevelSelectDialog = false) }
    }

    fun openSettings() {
        soundManager.playClick()
        _uiState.update { it.copy(showSettingsDialog = true) }
    }

    fun dismissSettings() {
        soundManager.playClick()
        _uiState.update { it.copy(showSettingsDialog = false) }
    }

    fun openAchievements() {
        soundManager.playClick()
        _uiState.update {
            it.copy(
                showAchievementsDialog = true,
                achievements = repository.getAchievements()
            )
        }
    }

    fun dismissAchievements() {
        soundManager.playClick()
        _uiState.update { it.copy(showAchievementsDialog = false) }
    }

    fun openDailyChallenge() {
        soundManager.playClick()
        val daily = DailyPuzzleGenerator.getDailyPuzzle()
        _uiState.update {
            it.copy(
                currentLevel = daily,
                showVictoryModal = false
            )
        }
    }

    fun onSoundChanged(enabled: Boolean) {
        soundManager.soundEffectsEnabled = enabled
    }

    fun onMusicChanged(enabled: Boolean) {
        soundManager.musicEnabled = enabled
    }

    fun onHapticsChanged(enabled: Boolean) {
        soundManager.hapticsEnabled = enabled
    }

    fun resetProgress() {
        repository.resetAllProgress()
        soundManager.playBuzzer()
        _uiState.value = createInitialState()
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.stopBackgroundMusic()
    }
}

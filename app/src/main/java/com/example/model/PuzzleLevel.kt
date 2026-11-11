package com.example.model

enum class PuzzleCategory(val displayName: String, val iconLabel: String) {
    FIND_OBJECT("Find Object", "🔍"),
    HELP_CHARACTER("Help Friend", "🐸"),
    FIX_MACHINE("Fix It", "⚙️"),
    TRICKY_MATH("Tricky Math", "🔢"),
    HIDDEN_OBJECT("Hidden Mystery", "🕵️"),
    AGE_TIME("Time Warp", "⏳"),
    MISSING_ITEM("Missing Item", "🧩"),
    ESCAPE("Escape Room", "🗝️"),
    PHYSICS("Cartoon Physics", "🎯"),
    UNEXPECTED_LOGIC("Twisted Logic", "💡")
}

enum class PuzzleType {
    FRUIT_SCALE,          // Level 1: Which is biggest?
    FROG_CROSSING,        // Level 2: Help frog cross pond
    LIGHTBULB_CIRCUIT,    // Level 3: Connect wire with paperclip
    MATCHSTICK_MATH,      // Level 4: 5 + 7 = 2 tricky equation
    SLEEPY_CAT,           // Level 5: Wake cat using hidden fish/sun
    ELEPHANT_FRIDGE,      // Level 6: Shrink elephant and put in fridge
    BALLOON_CACTUS,       // Level 7: Save balloon from sharp needle
    HIDDEN_KEY,           // Level 8: Uncover secret floor key
    SHAVE_GRANDPA,        // Level 9: Rub eraser on beard to make young
    HIGHEST_NUMBER,       // Level 10: Which number is the highest?
    GIRAFFE_NECK,         // Level 11: Stretch giraffe neck to reach leaves
    HATCH_CHICK,          // Level 12: Focus sun with glass or blanket
    PIPE_CONNECT,         // Level 13: Rotate water pipes
    BALANCE_SEESAW,       // Level 14: Balance heavy hippo and bird
    ODD_OWL_OUT,          // Level 15: Find the imposter owl/cat
    FLASHLIGHT_DARK,      // Level 16: Put batteries in flashlight
    DONUT_WHEEL,          // Level 17: Replace missing car tire with donut
    PRISON_ESCAPE,        // Level 18: Steal guard key without waking
    CATCH_APPLES,         // Level 19: Slide basket to catch falling apples
    TSHIRT_HOLES,         // Level 20: Tricky holes count on t-shirt
    WELL_KITTEN,          // Level 21: Rope and bucket to rescue kitten
    TRAFFIC_LIGHT,        // Level 22: Turn red light green
    FIND_STARS,           // Level 23: Spot 5 hidden stars
    MONSTER_BED,          // Level 24: Turn lamp on to reveal slippers
    VAULT_UNLOCKED,       // Level 25: Master vault code puzzle
    GENERATED_TRICK       // Procedurally generated endless daily trick
}

data class PuzzleLevel(
    val id: Int,
    val levelNumber: Int,
    val title: String,
    val category: PuzzleCategory,
    val question: String,
    val subClue: String = "",
    val hints: List<String>,
    val explanation: String,
    val puzzleType: PuzzleType,
    // Extra metadata for custom procedural puzzles if applicable
    val extraOptions: List<String> = emptyList(),
    val correctIndex: Int = 0
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val progress: Int = 0,
    val maxProgress: Int = 1
)

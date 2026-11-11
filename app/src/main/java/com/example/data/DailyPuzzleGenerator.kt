package com.example.data

import com.example.model.PuzzleCategory
import com.example.model.PuzzleLevel
import com.example.model.PuzzleType
import java.util.Calendar

object DailyPuzzleGenerator {

    private data class GeneratedTrick(
        val question: String,
        val clue: String,
        val options: List<String>,
        val correctIndex: Int,
        val hints: List<String>,
        val explanation: String,
        val category: PuzzleCategory
    )

    private val tricks = listOf(
        GeneratedTrick(
            question = "A rooster laid an egg on top of a slanted barn roof. Which way did it roll?",
            clue = "Think about farm animals carefully!",
            options = listOf("Rolled Left", "Rolled Right", "Roosters don't lay eggs!", "Fell straight down"),
            correctIndex = 2,
            hints = listOf("Does a rooster lay eggs?", "Only hens lay eggs!", "Roosters don't lay eggs at all!"),
            explanation = "Roosters are male chickens! Only hens lay eggs!",
            category = PuzzleCategory.UNEXPECTED_LOGIC
        ),
        GeneratedTrick(
            question = "If you overtake the runner in second place, what position are you in?",
            clue = "Trace the race track positions.",
            options = listOf("1st Place", "2nd Place", "3rd Place", "Last Place"),
            correctIndex = 1,
            hints = listOf("You passed the person in 2nd place.", "You took their spot!", "You are now in 2nd place!"),
            explanation = "By overtaking 2nd place, you become 2nd! The 1st runner is still ahead of you!",
            category = PuzzleCategory.TRICKY_MATH
        ),
        GeneratedTrick(
            question = "Mary's father has 5 daughters: Nana, Nene, Nini, Nono. What is the 5th daughter's name?",
            clue = "Re-read the first word of the question!",
            options = listOf("Nunu", "Mary", "Nancy", "Nina"),
            correctIndex = 1,
            hints = listOf("Look at who the father belongs to.", "It's MARY'S father!", "Her name is Mary!"),
            explanation = "Mary is the 5th daughter! Her father's daughters are Nana, Nene, Nini, Nono, and Mary!",
            category = PuzzleCategory.FIND_OBJECT
        ),
        GeneratedTrick(
            question = "How many months have 28 days?",
            clue = "Do some calendar inspection.",
            options = listOf("Only 1 (February)", "Only leap years", "All 12 months!", "6 months"),
            correctIndex = 2,
            hints = listOf("Does January have 28 days?", "Every month has at least 28 days!", "All 12 months have 28 days!"),
            explanation = "Every single month has at least 28 days! Some just have 30 or 31 too!",
            category = PuzzleCategory.AGE_TIME
        ),
        GeneratedTrick(
            question = "What can you hold in your left hand, but NEVER in your right hand?",
            clue = "Look down at your hands right now.",
            options = listOf("A hot cup", "Your right elbow", "Your smartphone", "A pencil"),
            correctIndex = 1,
            hints = listOf("Try holding your own joints.", "Can your right hand touch your right elbow?", "Your right elbow cannot be held by your right hand!"),
            explanation = "Physically impossible to hold your right elbow with your right hand!",
            category = PuzzleCategory.PHYSICS
        ),
        GeneratedTrick(
            question = "What has hands and a face, but cannot hold anything or smile?",
            clue = "Tick-tock...",
            options = listOf("A doll", "A wall clock", "A statue", "A mirror"),
            correctIndex = 1,
            hints = listOf("It tells you something important.", "It has an hour hand and a minute hand.", "A clock!"),
            explanation = "A clock has an hour hand, minute hand, and a clock face!",
            category = PuzzleCategory.MISSING_ITEM
        ),
        GeneratedTrick(
            question = "What gets wetter the more it dries?",
            clue = "You use it after a shower.",
            options = listOf("Water", "A sponge", "A bath towel", "The sun"),
            correctIndex = 2,
            hints = listOf("It dries things off.", "As it absorbs water, it gets wetter.", "A towel!"),
            explanation = "A towel gets wetter as it dries your body!",
            category = PuzzleCategory.UNEXPECTED_LOGIC
        ),
        GeneratedTrick(
            question = "You walk into a cold dark cabin. You have one match. There is an oil lamp, a fireplace, and a candle. What do you light FIRST?",
            clue = "How does fire start?",
            options = listOf("The oil lamp", "The fireplace", "The candle", "The match!"),
            correctIndex = 3,
            hints = listOf("What lights the other items?", "You can't light anything without striking the match first!", "Light the match first!"),
            explanation = "You must light the match before you can light anything else!",
            category = PuzzleCategory.ESCAPE
        ),
        GeneratedTrick(
            question = "Before Mount Everest was discovered, what was the highest mountain on Earth?",
            clue = "Did the mountain exist before being discovered?",
            options = listOf("K2", "Mount Kilimanjaro", "Mount Everest", "Mont Blanc"),
            correctIndex = 2,
            hints = listOf("Discovery doesn't create mountains.", "It was always there!", "Mount Everest was still the highest, just undiscovered!"),
            explanation = "Mount Everest was still the highest mountain, humans just hadn't discovered it yet!",
            category = PuzzleCategory.FIND_OBJECT
        ),
        GeneratedTrick(
            question = "If an electric train is traveling north at 100 mph and wind is blowing south at 20 mph, which way does the smoke blow?",
            clue = "What type of train is it?",
            options = listOf("South", "North", "East", "There is no smoke!"),
            correctIndex = 3,
            hints = listOf("Read the train engine type.", "It's an ELECTRIC train!", "Electric trains produce no smoke!"),
            explanation = "Electric trains don't produce any smoke!",
            category = PuzzleCategory.PHYSICS
        )
    )

    fun getDailyPuzzle(): PuzzleLevel {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val trick = tricks[dayOfYear % tricks.size]

        return PuzzleLevel(
            id = 1000 + dayOfYear,
            levelNumber = dayOfYear,
            title = "Daily Brain Teaser",
            category = trick.category,
            question = trick.question,
            subClue = trick.clue,
            hints = trick.hints,
            explanation = trick.explanation,
            puzzleType = PuzzleType.GENERATED_TRICK,
            extraOptions = trick.options,
            correctIndex = trick.correctIndex
        )
    }

    fun getGeneratedLevel(levelNumber: Int): PuzzleLevel {
        val trick = tricks[(levelNumber - 1) % tricks.size]
        return PuzzleLevel(
            id = levelNumber,
            levelNumber = levelNumber,
            title = "Level $levelNumber",
            category = trick.category,
            question = trick.question,
            subClue = trick.clue,
            hints = trick.hints,
            explanation = trick.explanation,
            puzzleType = PuzzleType.GENERATED_TRICK,
            extraOptions = trick.options,
            correctIndex = trick.correctIndex
        )
    }
}

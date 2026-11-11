package com.example.data

import com.example.model.PuzzleCategory
import com.example.model.PuzzleLevel
import com.example.model.PuzzleType

object LevelLibrary {

    val levels: List<PuzzleLevel> = listOf(
        // Level 1
        PuzzleLevel(
            id = 1,
            levelNumber = 1,
            title = "Size Matters",
            category = PuzzleCategory.FIND_OBJECT,
            question = "Which one is the BIGGEST?",
            subClue = "Look closely at everything on the screen!",
            hints = listOf(
                "Are you only looking at the fruits?",
                "Think about real-world scale vs drawing scale.",
                "Look up in the sky! The Sun is over a million kilometers wide! Tap the Sun!"
            ),
            explanation = "The Sun in the sky is millions of times larger than any fruit or animal on Earth!",
            puzzleType = PuzzleType.FRUIT_SCALE
        ),

        // Level 2
        PuzzleLevel(
            id = 2,
            levelNumber = 2,
            title = "Pond Crossing",
            category = PuzzleCategory.HELP_CHARACTER,
            question = "Help the frog jump safely across the pond!",
            subClue = "Beware of the hungry crocodile!",
            hints = listOf(
                "If the frog jumps now, the crocodile will snap!",
                "Can you change the environment?",
                "Drag the cloud over the sun to make shade so the crocodile falls asleep, or move the lilypad!"
            ),
            explanation = "Putting the crocodile to sleep or moving the lilypad cleared a safe path for our little frog friend!",
            puzzleType = PuzzleType.FROG_CROSSING
        ),

        // Level 3
        PuzzleLevel(
            id = 3,
            levelNumber = 3,
            title = "Bright Idea",
            category = PuzzleCategory.FIX_MACHINE,
            question = "Turn on the lightbulb!",
            subClue = "The electrical wire is cut!",
            hints = listOf(
                "Electricity needs a conductor to flow through the gap.",
                "Which item on the notebook desk conducts electricity?",
                "Drag the metal paperclip into the wire gap to complete the electric circuit!"
            ),
            explanation = "Metal conducts electricity! The paperclip closed the circuit and illuminated the bulb!",
            puzzleType = PuzzleType.LIGHTBULB_CIRCUIT
        ),

        // Level 4
        PuzzleLevel(
            id = 4,
            levelNumber = 4,
            title = "Matchstick Logic",
            category = PuzzleCategory.TRICKY_MATH,
            question = "Make the equation true: 5 + 7 = 2",
            subClue = "Move just ONE matchstick!",
            hints = listOf(
                "Can you change the plus sign into a minus?",
                "Where can that extra matchstick go?",
                "Drag the vertical stick from '+' to the front of 5 to make: -5 + 7 = 2!"
            ),
            explanation = "-5 + 7 = 2! Negative numbers save the day!",
            puzzleType = PuzzleType.MATCHSTICK_MATH
        ),

        // Level 5
        PuzzleLevel(
            id = 5,
            levelNumber = 5,
            title = "Wakey Wakey",
            category = PuzzleCategory.HELP_CHARACTER,
            question = "Wake up the sleepy student!",
            subClue = "He is sleeping through his morning exam!",
            hints = listOf(
                "He's a very heavy sleeper.",
                "Sunlight is the best natural alarm.",
                "Swipe the window curtains open to let the bright sunlight wake him up!"
            ),
            explanation = "Bright morning sunlight woke him right up! Time to ace the test!",
            puzzleType = PuzzleType.SLEEPY_CAT
        ),

        // Level 6
        PuzzleLevel(
            id = 6,
            levelNumber = 6,
            title = "Impossible Fridge",
            category = PuzzleCategory.UNEXPECTED_LOGIC,
            question = "Put the huge elephant into the fridge!",
            subClue = "It doesn't seem to fit at all...",
            hints = listOf(
                "First, you have to open the fridge door.",
                "How do you make big things small on a touchscreen?",
                "Open the fridge door, then pinch the elephant (or tap it twice) to shrink it down, then drag it inside!"
            ),
            explanation = "Pinch to shrink! In cartoon world, touchscreen magic works on elephants!",
            puzzleType = PuzzleType.ELEPHANT_FRIDGE
        ),

        // Level 7
        PuzzleLevel(
            id = 7,
            levelNumber = 7,
            title = "Pop Protection",
            category = PuzzleCategory.PHYSICS,
            question = "Save the red balloon from popping!",
            subClue = "It is drifting right toward sharp spikes!",
            hints = listOf(
                "Balloons naturally float upwards.",
                "Can you protect the sharp spikes?",
                "Drag the protective cork over the ceiling spikes before the balloon hits them!"
            ),
            explanation = "Capping the spikes saved the balloon! Safe and sound in the air!",
            puzzleType = PuzzleType.BALLOON_CACTUS
        ),

        // Level 8
        PuzzleLevel(
            id = 8,
            levelNumber = 8,
            title = "The Secret Safe",
            category = PuzzleCategory.HIDDEN_OBJECT,
            question = "Find the hidden key to open the treasure chest!",
            subClue = "It's hidden somewhere in this room.",
            hints = listOf(
                "Objects on the desk can be moved around.",
                "Try looking underneath the potted plant.",
                "Drag the potted plant aside to reveal the brass key, then drag it to the chest!"
            ),
            explanation = "You moved the plant and found the secret stash! Pure detective skills!",
            puzzleType = PuzzleType.HIDDEN_KEY
        ),

        // Level 9
        PuzzleLevel(
            id = 9,
            levelNumber = 9,
            title = "Youth Fountain",
            category = PuzzleCategory.AGE_TIME,
            question = "Make grandpa look 30 years younger!",
            subClue = "That bushy white beard adds decades!",
            hints = listOf(
                "This is drawn with pencil and ink on notebook paper.",
                "What stationery item removes pencil marks?",
                "Pick up the eraser and rub it vigorously over grandpa's beard to erase it!"
            ),
            explanation = "Rubbing the beard erased 30 years! He looks so fresh and cheerful!",
            puzzleType = PuzzleType.SHAVE_GRANDPA
        ),

        // Level 10
        PuzzleLevel(
            id = 10,
            levelNumber = 10,
            title = "Highest Altitude",
            category = PuzzleCategory.UNEXPECTED_LOGIC,
            question = "Which number is the HIGHEST?",
            subClue = "Don't just think about numerical value...",
            hints = listOf(
                "Is 'highest' about quantity or vertical position?",
                "Look at the vertical height on the notebook page.",
                "The number 7 is positioned at the very top of the page! Tap 7!"
            ),
            explanation = "7 is physically the highest up on the screen! You think outside the box!",
            puzzleType = PuzzleType.HIGHEST_NUMBER
        ),

        // Level 11
        PuzzleLevel(
            id = 11,
            levelNumber = 11,
            title = "Tall Order",
            category = PuzzleCategory.HELP_CHARACTER,
            question = "Feed the hungry giraffe!",
            subClue = "The juiciest leaves are way up at the top.",
            hints = listOf(
                "The giraffe's neck is too short right now.",
                "Cartoons have stretchy physics!",
                "Drag the giraffe's head upward to stretch its neck all the way to the tree top!"
            ),
            explanation = "Giraffe necks are made of rubber in cartoons! Munch munch munch!",
            puzzleType = PuzzleType.GIRAFFE_NECK
        ),

        // Level 12
        PuzzleLevel(
            id = 12,
            levelNumber = 12,
            title = "Happy Hatching",
            category = PuzzleCategory.PHYSICS,
            question = "Help the little chick hatch from its egg!",
            subClue = "The egg is shivering from the cold.",
            hints = listOf(
                "The egg needs warmth to hatch.",
                "How do you concentrate warmth or cover it?",
                "Drag the cozy yellow blanket over the egg to keep it warm, then tap to hatch!"
            ),
            explanation = "Cozy warmth did the trick! Welcome to the world, little chick!",
            puzzleType = PuzzleType.HATCH_CHICK
        ),

        // Level 13
        PuzzleLevel(
            id = 13,
            levelNumber = 13,
            title = "Plumber Puzzle",
            category = PuzzleCategory.FIX_MACHINE,
            question = "Fix the pipes to water the thirsty sunflower!",
            subClue = "Tap pipe joints to rotate them into alignment.",
            hints = listOf(
                "Water must flow from the faucet to the flower pot.",
                "Tap each pipe corner to rotate it 90 degrees.",
                "Align all 4 pipe elbows so water flows seamlessly, then turn on the tap!"
            ),
            explanation = "Water is flowing and the sunflower is dancing with joy!",
            puzzleType = PuzzleType.PIPE_CONNECT
        ),

        // Level 14
        PuzzleLevel(
            id = 14,
            levelNumber = 14,
            title = "Balance Act",
            category = PuzzleCategory.PHYSICS,
            question = "Balance the seesaw!",
            subClue = "The hippo weighs way too much for the little bird.",
            hints = listOf(
                "Adding more weights to the bird won't be enough.",
                "Who says the hippo has to stay on the seesaw?",
                "Drag the hippo right off the seesaw onto the grass!"
            ),
            explanation = "Removed the heavyweight champion! The seesaw is peacefully balanced!",
            puzzleType = PuzzleType.BALANCE_SEESAW
        ),

        // Level 15
        PuzzleLevel(
            id = 15,
            levelNumber = 15,
            title = "Spot the Imposter",
            category = PuzzleCategory.FIND_OBJECT,
            question = "Find the sneaky imposter among the owls!",
            subClue = "One of these owls is not what it seems...",
            hints = listOf(
                "Look closely at their faces and ears.",
                "Does an owl have whiskers and feline ears?",
                "The owl on the far right has cat whiskers and meows when tapped! Tap it!"
            ),
            explanation = "Meow! That sneaky kitty disguised itself as an owl to catch mice!",
            puzzleType = PuzzleType.ODD_OWL_OUT
        ),

        // Level 16
        PuzzleLevel(
            id = 16,
            levelNumber = 16,
            title = "Spooky Trail",
            category = PuzzleCategory.ESCAPE,
            question = "Light up the spooky dark forest!",
            subClue = "The flashlight has no batteries inside.",
            hints = listOf(
                "You need two AA batteries to power the torch.",
                "One battery is on the ground, but where is the second?",
                "One battery is behind the tree trunk! Drag both batteries into the flashlight and tap switch!"
            ),
            explanation = "Flashlight powered! The dark shadows scurry away into the night!",
            puzzleType = PuzzleType.FLASHLIGHT_DARK
        ),

        // Level 17
        PuzzleLevel(
            id = 17,
            levelNumber = 17,
            title = "Delicious Wheel",
            category = PuzzleCategory.MISSING_ITEM,
            question = "Help the racecar reach the finish line!",
            subClue = "It's missing its rear wheel!",
            hints = listOf(
                "A wheel needs to be perfectly round to roll.",
                "Look at the items around the track: brick, triangle, chocolate donut...",
                "Drag the round chocolate donut onto the wheel axle, then tap the gas pedal!"
            ),
            explanation = "A chocolate donut makes a surprisingly tasty tire! Vroom vroom!",
            puzzleType = PuzzleType.DONUT_WHEEL
        ),

        // Level 18
        PuzzleLevel(
            id = 18,
            levelNumber = 18,
            title = "Sneaky Jailbreak",
            category = PuzzleCategory.ESCAPE,
            question = "Help the prisoner escape without waking the guard!",
            subClue = "The guard is asleep on his chair, holding the key on his belt.",
            hints = listOf(
                "Do not tap the guard or he will wake up!",
                "You can quietly steal items from his belt.",
                "Carefully drag the brass key from his belt straight to the cell padlock!"
            ),
            explanation = "Stealth 100! Sneaked out right under the guard's snoring nose!",
            puzzleType = PuzzleType.PRISON_ESCAPE
        ),

        // Level 19
        PuzzleLevel(
            id = 19,
            levelNumber = 19,
            title = "Harvest Time",
            category = PuzzleCategory.PHYSICS,
            question = "Catch 5 juicy falling apples!",
            subClue = "Slide the basket left and right under the tree.",
            hints = listOf(
                "Keep your finger on the basket and follow the apples.",
                "Don't let them hit the ground!",
                "Slide the basket smoothly back and forth to catch all 5 apples!"
            ),
            explanation = "Fresh apple pie tonight! All 5 juicy apples caught successfully!",
            puzzleType = PuzzleType.CATCH_APPLES
        ),

        // Level 20
        PuzzleLevel(
            id = 20,
            levelNumber = 20,
            title = "Holey Shirt",
            category = PuzzleCategory.TRICKY_MATH,
            question = "How many holes does this t-shirt have?",
            subClue = "Look through the shirt and don't forget the obvious ones!",
            hints = listOf(
                "Every t-shirt already has neck, waist, and two sleeve openings.",
                "Notice you can see straight through the holes in the middle...",
                "4 body openings + 2 rips going through both front & back (4 hole edges) = 8 holes! Tap 8!"
            ),
            explanation = "Neck (1) + Waist (1) + Sleeves (2) + 2 ripped holes through both sides (4) = 8 holes!",
            puzzleType = PuzzleType.TSHIRT_HOLES,
            extraOptions = listOf("4", "6", "8", "10"),
            correctIndex = 2
        ),

        // Level 21
        PuzzleLevel(
            id = 21,
            levelNumber = 21,
            title = "Well Rescue",
            category = PuzzleCategory.HELP_CHARACTER,
            question = "Rescue the poor kitten stuck in the well!",
            subClue = "The kitten is meowing from deep inside the stone well.",
            hints = listOf(
                "You need a bucket and a rope to lower down.",
                "Attach the rope to the bucket first.",
                "Connect rope to bucket, drag bucket into the well, wait for kitten to hop in, then wind crank!"
            ),
            explanation = "Kitten safely rescued! Purring warmly in your arms!",
            puzzleType = PuzzleType.WELL_KITTEN
        ),

        // Level 22
        PuzzleLevel(
            id = 22,
            levelNumber = 22,
            title = "Traffic Jam",
            category = PuzzleCategory.FIX_MACHINE,
            question = "Turn the traffic light GREEN to clear traffic!",
            subClue = "The light is stuck on red and drivers are angry.",
            hints = listOf(
                "You have yellow and blue paint pots on the sidewalk.",
                "What color do blue and yellow make when combined?",
                "Mix the blue and yellow paint on the light lens to create green!"
            ),
            explanation = "Yellow + Blue = Green! Color theory saves the morning commute!",
            puzzleType = PuzzleType.TRAFFIC_LIGHT
        ),

        // Level 23
        PuzzleLevel(
            id = 23,
            levelNumber = 23,
            title = "Constellation Hunt",
            category = PuzzleCategory.HIDDEN_OBJECT,
            question = "Find all 5 hidden doodle stars!",
            subClue = "They are camouflaged around the notebook page.",
            hints = listOf(
                "Check the notebook corners and behind the stationery items.",
                "One is on the pencil holder, one on the ruler, one in the book...",
                "Tap all 5 doodle stars to highlight them!"
            ),
            explanation = "Eagle eyes! You found every cleverly hidden star doodle!",
            puzzleType = PuzzleType.FIND_STARS
        ),

        // Level 24
        PuzzleLevel(
            id = 24,
            levelNumber = 24,
            title = "Monster Under Bed",
            category = PuzzleCategory.ESCAPE,
            question = "Scare away the monster under the bed!",
            subClue = "Creepy glowing eyes are peeking out from the dark!",
            hints = listOf(
                "Monsters love darkness.",
                "Look at the lamp on the bedside nightstand.",
                "Pull the lamp string or tap switch to turn on the light and reveal what it really is!"
            ),
            explanation = "No monster! Just cute fuzzy bunny slippers with glow-in-the-dark eyes!",
            puzzleType = PuzzleType.MONSTER_BED
        ),

        // Level 25
        PuzzleLevel(
            id = 25,
            levelNumber = 25,
            title = "The Brain Vault",
            category = PuzzleCategory.UNEXPECTED_LOGIC,
            question = "Unlock the Master Brain Vault!",
            subClue = "Clue: 'First Think, then Shine, then Wonder!'",
            hints = listOf(
                "Match the 3 dial symbols to the clue words.",
                "'Think' is a Brain, 'Shine' is a Lightbulb, 'Wonder' is a Star.",
                "Rotate Dial 1 to Brain, Dial 2 to Lightbulb, Dial 3 to Star, then pull the golden handle!"
            ),
            explanation = "Click! The vault door swings wide open revealing the Golden Brain Trophy!",
            puzzleType = PuzzleType.VAULT_UNLOCKED
        )
    )

    fun getLevel(index: Int): PuzzleLevel {
        val safeIndex = index.coerceIn(0, levels.lastIndex)
        return levels[safeIndex]
    }
}

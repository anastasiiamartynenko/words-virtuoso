package wordsvirtuoso.controllers

import wordsvirtuoso.managers.CharManager
import wordsvirtuoso.managers.WordManager
import wordsvirtuoso.utils.CharState
import kotlin.system.exitProcess

class GameController(
    private val secretWord: String,
    private val allWords: List<String>,
) {
    val wordManager = WordManager(allWords)
    val charManager = CharManager()

    // Variables for tracking the user's behavior.
    private var numberOfTurns: Int = 0
    private var start = System.currentTimeMillis()
    private val clueStrings = mutableListOf<String>()
    private var wrongCharacters = ""

    /**
     * Print messages in a successful situation.
     */
    private fun printSuccess(word: String, duration: Int? = null) {
        val firstTry = duration == null

        if (!firstTry) {
            for (str in clueStrings) {
                println(str)
            }
        }
        println(word)
        println("Correct!")

        println(
            if (firstTry) "Amazing luck! The solution was found at once."
            else "The solution was found after $numberOfTurns tries in $duration seconds."
        )
    }

    /**
     * Print clue strings.
     */
    private fun printClues() {
        for (str in clueStrings) { println(str) }
        println(charManager.colorChar<String>(sortString(wrongCharacters), CharState.LEFTOVERS))
    }

    /**
     * Generate clue strings.
     */
    private fun generateClueString(userInput: String): String {
        var generatedString = ""

        for ((index, char) in userInput.withIndex()) {
            generatedString += if (secretWord.indexOf(char) == index) {
                charManager.colorChar<Char>(char, CharState.CORRECT)
            } else if (secretWord.contains(char)) {
                charManager.colorChar<Char>(char, CharState.WRONG_POSITION)
            } else {
                charManager.colorChar<Char>(char, CharState.WRONG)
            }
        }

        return generatedString
    }

    /**
     * Sort letters in the string (ASC).
     */
    private fun sortString(input: String): String {
        return input.toCharArray().sorted().joinToString("")
    }

    /**
     * Get the wrong characters.
     */
    private fun getWrongCharacters(secretWord: String, userInput: String, previousCharacters: String): String {
        var result = previousCharacters

        for (char in userInput) {
            if (!secretWord.contains(char, ignoreCase = true) && !result.contains(char, ignoreCase = true)) {
                result += char
            }
        }

        return result.uppercase()
    }

    init {
        while (true) {
            numberOfTurns++

            // Read input.
            println("Input a 5-letter word:")
            val userInput = readln().uppercase().trim()

            // Exit the game on user's demand.
            if (userInput == "EXIT") {
                println("The game is over.")
                exitProcess(0)
            } else {
                // Finish the game (successful situation).
                if (userInput == secretWord) {
                    val end = System.currentTimeMillis()
                    val duration = (end - start) / 1000

                    if (numberOfTurns == 1) {
                        printSuccess(wordManager.colorCharsInWord(userInput, CharState.CORRECT))
                    } else {
                        printSuccess(wordManager.colorCharsInWord(userInput, CharState.CORRECT), duration.toInt())
                    }
                    exitProcess(0)
                }

                // Validate user input.
                val inputValidationResult = wordManager.validate(userInput)

                if (inputValidationResult != null) {
                    // Handle the situation when user's input is invalid.
                    println(inputValidationResult)
                } else {
                    // Handle the situation when user's input is valid but incorrect (the secret word is not guessed).
                    clueStrings.add(generateClueString(userInput))
                    wrongCharacters = getWrongCharacters(secretWord, userInput, wrongCharacters)

                    printClues()
                }
            }
        }
    }
}
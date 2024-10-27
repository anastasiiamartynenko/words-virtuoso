package wordsvirtuoso.controllers

import wordsvirtuoso.managers.FileManager
import wordsvirtuoso.managers.Manager
import wordsvirtuoso.managers.WordManager
import wordsvirtuoso.utils.FileType
import kotlin.random.Random

class FilesController(
    private val allWordsFilename: String,
    private val candidatesFilename: String
): Manager() {
    private val allWordsFileManager = FileManager(allWordsFilename, FileType.ALL_WORDS)
    private val candidatesFileManager = FileManager(candidatesFilename, FileType.CANDIDATES)
    val allWords = allWordsFileManager.lines
    private val candidates = candidatesFileManager.lines

    /**
     * Validate the lines of the file.
     */
    private fun validateLines(lines: List<String>, superSetLines: List<String>?): Int {
        val wordManager = WordManager(superSetLines)
        var numberOfErrors = 0

        for (line in lines) {
            if (wordManager.validate(line) != null) numberOfErrors++
        }

        return numberOfErrors
    }

    /**
     * Get amount of the candidate words which are not included in the all words.
     */
    private fun getAmountOfExcludedWords(): Int {
        var amount = 0

        for (word in candidates) {
            if (!allWords.contains(word)) amount++
        }

        return amount
    }

    /**
     * Validate the files.
     */
    override fun validate(value: String): String? {
        // Check whether all the words in the all words file are valid.
        val numberOfAllWordsErrors = validateLines(allWords, null)
        if (numberOfAllWordsErrors > 0) {
            return "Error: $numberOfAllWordsErrors invalid words were found in the $allWordsFilename file."
        }

        // Check if all the words in the candidates file are valid.
        val numberOfCandidatesErrors = validateLines(candidates, null)
        if (numberOfCandidatesErrors > 0) {
            return "Error: $numberOfCandidatesErrors invalid words were found in the $candidatesFilename file."
        }

        // Check whether there are the words in the candidates file which are not included in the all words file.
        val amountOfExcludedWords = getAmountOfExcludedWords()
        if (amountOfExcludedWords > 0) {
            return "Error: $amountOfExcludedWords candidate words are not included in the $allWordsFilename file."
        }

        return null
    }

    /**
     * Generate a random word from the candidates.
     */
    fun getRandomWord(): String {
        val randomNumber = Random.nextInt(0, candidates.size)
        return candidates[randomNumber].uppercase()
    }
}
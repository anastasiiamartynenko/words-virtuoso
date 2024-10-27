package wordsvirtuoso.managers

import wordsvirtuoso.utils.CharState

class WordManager (
    private val allWords: List<String>?
): Manager() {
    private val charManager: CharManager = CharManager()
    private fun hasDuplicates(str: String) = str.toSet().size < str.length

    override fun validate(value: String): String? {
        val validStringRegex = "[a-zA-Z]+".toRegex()

        return when {
            value.length != 5 -> "The input isn't a 5-letter word."
            !value.matches(validStringRegex) -> "One or more letters of the input aren't valid."
            hasDuplicates(value) -> "The input has duplicate letters."
            allWords != null && !allWords.contains(value) -> "The input word isn't included in my words list."
            else -> null
        }
    }

    fun colorCharsInWord(word: String, charState: CharState): String = word.map {
        charManager.colorChar(it, charState)
    }.joinToString("")
}
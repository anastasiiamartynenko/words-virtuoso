package wordsvirtuoso.managers

import wordsvirtuoso.utils.CharState

fun <T> replace(src: T, wrapper: String): String = wrapper.replace("<letter>", src.toString())

class CharManager {
    private val correctColored = "\u001B[48:5:10m<letter>\u001B[0m"
    private val wrongPositionColored = "\u001B[48:5:11m<letter>\u001B[0m"
    private val wrongColored = "\u001B[48:5:7m<letter>\u001B[0m"
    private val leftoversColored = "\u001B[48:5:14m<letter>\u001B[0m"

    fun <T> colorChar(char: T, charState: CharState) = when(charState) {
        CharState.CORRECT -> replace<T>(char, correctColored)
        CharState.WRONG_POSITION -> replace<T>(char, wrongPositionColored)
        CharState.WRONG -> replace<T>(char, wrongColored)
        CharState.LEFTOVERS -> replace<T>(char, leftoversColored)
    }
}
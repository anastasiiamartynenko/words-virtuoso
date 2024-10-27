package wordsvirtuoso.utils

enum class FileType {
    ALL_WORDS, CANDIDATES;

    companion object {
        val fileTypes by lazy { FileType.values().map { it.name } }
    }
}

enum class CharState {
    CORRECT, WRONG_POSITION, WRONG, LEFTOVERS;

    companion object {
        val charStates by lazy { CharState.values().map { it.name } }
    }
}
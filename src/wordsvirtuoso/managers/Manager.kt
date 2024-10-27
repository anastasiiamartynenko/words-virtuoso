package wordsvirtuoso.managers

abstract class Manager {
    abstract fun validate(value: String = ""): String?
}
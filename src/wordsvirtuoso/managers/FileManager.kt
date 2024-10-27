package wordsvirtuoso.managers

import wordsvirtuoso.utils.FileType
import java.io.File
import kotlin.system.exitProcess

class FileManager(
    private val filename: String,
    filetype: FileType,
) {
    private val file = File(filename)
    private val prefix = if (filetype == FileType.ALL_WORDS) "" else "candidate "
    var lines = listOf<String>()

    init {
        // Check whether the file exists.
        if (!file.exists()) {
            println("Error: The ${prefix}words file $filename doesn't exist.")
            exitProcess(0)
        } else {
            lines = file.readLines().map { it.uppercase() }
        }
    }
}
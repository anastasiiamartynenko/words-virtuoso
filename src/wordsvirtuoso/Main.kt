package wordsvirtuoso

import wordsvirtuoso.controllers.FilesController
import wordsvirtuoso.controllers.GameController

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Error: Wrong number of arguments.")
        return
    }

    // Validate files.
    val filesController = FilesController(
        args[0],
        args[1],
    )

    val filesValidationResult = filesController.validate()
    if (filesValidationResult != null) {
        println(filesValidationResult)
        return
    }

    // Begin the game.
    println("Words Virtuoso")

    GameController(
        filesController.getRandomWord(),
        filesController.allWords
    )
}
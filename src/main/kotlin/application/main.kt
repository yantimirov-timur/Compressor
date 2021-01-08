package application

import huffman.HuffmanMain
import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File

class Settings {
    @Option(name = "-c", usage = "Compress")
    var encode = false

    @Option(name = "-d", usage = "Long flag")
    var decode = false

    @Argument(metaVar = "Name")
    var fileName = ""
}

fun main(args: Array<String>) {
    val settings = Settings()
    val huffmanMain = HuffmanMain()
    val parser = CmdLineParser(settings)
    val list = mutableListOf<String>()
    args.toCollection(list)
    parser.parseArgument(list)
    val inputFile: File
    val outputFile: File

    when {
        settings.encode -> {
            inputFile = File(settings.fileName)
            outputFile = File("output")
            huffmanMain.encode(inputFile, outputFile)
        }

        settings.decode -> {
            inputFile = File(settings.fileName)
            outputFile = File("encoded.txt")

            huffmanMain.decode(inputFile, outputFile)
        }
    }
}
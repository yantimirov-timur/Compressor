package deflate

import huffmanByte.HuffmanMain
import java.io.File

fun main() {
    val input = File("input.txt")
    val output = File("output")
    val newFile = File("coded.txt")


    // println((input.length() / 1024).toDouble())
    //p/rintln((output.length() / 1024).toDouble())

    val huffmanMain = HuffmanMain()


  // huffmanMain.decode(output, newFile)
    huffmanMain.encode(input, output)
}

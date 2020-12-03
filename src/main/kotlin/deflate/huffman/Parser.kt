package deflate.huffman

import java.io.File

/**
 * Класс отвечающий за обработку текста
 */
class Parser {
    var input = File("input//inputText").readText()
    val frequencyTable = countFrequency(input)

    fun countFrequency(text: String): Map<Char, Int> {
        val result = mutableMapOf<Char, Int>()
        for (char in text) {
            if (char in result)
                result[char] = result[char]!! + 1
            else
                result[char] = 1
        }

        return result
    }
}
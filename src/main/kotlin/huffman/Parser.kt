package huffman

/**
 * Класс отвечающий за обработку текста
 */
class Parser {
    /**
     * Метод подсчитывает сколько раз встречается каждый символ
     */
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
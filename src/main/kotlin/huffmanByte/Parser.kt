package huffmanByte

/**
 * Класс отвечающий за обработку текста
 */
class Parser {
    /**
     * Метод подсчитывает сколько раз встречается каждый символ
     */
    fun countFrequency(text: ByteArray): Map<Byte, Int> {
        val result = mutableMapOf<Byte, Int>()

        for (element in text) {
            if (result.containsKey(element))
                result[element] = result[element]!! + 1
            else
                result[element] = 1
        }
        return result
    }
}
package huffman

class HuffmanSerializer {

    /**
     * Сериализация
     */
    fun serialize(countMap: Map<Byte, Int>, encodedText: BitString): ByteArray {
        val list = mutableListOf<Byte>()
        SIGN.forEach { list.add(it) }
        var numberOfCodeWords = countMap.size
        var numberOfBits: Int = encodedText.length()

        list.add((numberOfCodeWords and 0xff).toByte())
        for (i in 0..2) {
            list.add((8.let { numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords } and 0xff).toByte())
        }

        list.add((numberOfBits and 0xff).toByte())
        for (i in 0..2) {
            list.add((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())
        }

        for (entry in countMap.entries) {
            val character = entry.key
            var frequency = entry.value

            list.add(character)
            list.add(frequency.toByte())

            for (i in 0..2) {
                list.add((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())
            }
        }
        val encodedTextBytes = encodedText.toByteArray()
        encodedTextBytes.forEach { list.add(it) }

        return list.toByteArray()
    }

    companion object {
        /**
         * сигнатура файла для распознавания типа файла.
         */
        val SIGN = byteArrayOf(0xC0.toByte(), 0xDE.toByte(), 0x0D.toByte(), 0xDE.toByte())

        /**
         * Число байтов, необходимое для сериализации одного сопоставления символа с его кодовым словом.
         */
        const val BYTES_PER_WEIGHT_MAP_ENTRY = 5

        /**
         * Количество байтов, необходимое для сериализации количества кодовых слов.
         */
        const val BYTES_PER_CODE_WORD_COUNT_ENTRY = 4

        /**
         * Количество байтов, необходимое для сериализации количества битов в фактическом закодированном тексте.
         */
        const val BYTES_PER_BIT_COUNT_ENTRY = 4
    }
}

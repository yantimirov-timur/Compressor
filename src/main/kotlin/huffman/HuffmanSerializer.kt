package huffman

class HuffmanSerializer {

    /**
     * Сериализация
     */
    fun serialize(countMap: Map<Byte, Int>, encodedText: BitString): ByteArray {
        val list = mutableListOf<Byte>()
        MAGIC.forEach { list.add(it) }
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
         * The magic file signature for recognizing the file type.
         */
        val MAGIC = byteArrayOf(0xC0.toByte(), 0xDE.toByte(), 0x0D.toByte(), 0xDE.toByte())

        /**
         * The number of bytes it takes to serialize one mapping from a character
         * to its code word.
         */
        const val BYTES_PER_WEIGHT_MAP_ENTRY = 5

        /**
         * The number of bytes it takes to serialize the number of code words.
         */
        const val BYTES_PER_CODE_WORD_COUNT_ENTRY = 4

        /**
         * The number of bytes it takes to serialize the number of bits in the actual encoded text.
         */
        const val BYTES_PER_BIT_COUNT_ENTRY = 4
    }
}

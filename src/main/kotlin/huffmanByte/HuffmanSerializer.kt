package huffmanByte

import java.io.Serializable


class HuffmanSerializer : Serializable {
    /**
     * Produces a byte array holding the compressed text along with it encoder map.
     *
     * @param countMap the encoder map used for encoding the text.
     * @param encodedText  the encoded text.
     * @return an array of byte.
     */

    fun serialize(countMap: Map<Byte, Int>, encodedText: BitString): ByteArray {
        //  val byteList = ByteList(computeByteListSize(countMap, encodedText))

        val list = mutableListOf<Byte>()

        // Emit the magic number:
        for (b in MAGIC) {
            //  byteList.appendByte(b)
            list.add(b)
        }
        var numberOfCodeWords = countMap.size
        var numberOfBits: Int = encodedText.length()

        // Emit the number of code words.
//        byteList.appendByte((numberOfCodeWords and 0xff).toByte())
//        byteList.appendByte((8.let {
//            numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords
//        } and 0xff).toByte())
//        byteList.appendByte((8.let {
//            numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords
//        } and 0xff).toByte())
//        byteList.appendByte((8.let {
//            numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords
//        } and 0xff).toByte())


        list.add((numberOfBits and 0xff).toByte())
        //list.add((8.let { numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords } and 0xff).toByte())
        //list.add((8.let { numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords } and 0xff).toByte())
        list.add((8.let { numberOfCodeWords = numberOfCodeWords shr it; numberOfCodeWords } and 0xff).toByte())


        // Emit the number of bits in the encoded text.
        //   byteList.appendByte((numberOfBits and 0xff).toByte())
        //    byteList.appendByte((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())
        // byteList.appendByte((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())
        // byteList.appendByte((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())

        list.add((numberOfBits and 0xff).toByte())
        //   list.add((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())
        // list.add((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())
        list.add((8.let { numberOfBits = numberOfBits shr it; numberOfBits } and 0xff).toByte())


        // Emit the code words:
        for (entry in countMap.entries) {
            val character = entry.key
            var frequency = entry.value

            // Emit the character:
            //  byteList.appendByte(character)
            list.add(character)

            // Emit the bytes of the weight value: byteList.appendByte((frequency and 0xff).toByte())
//            byteList.appendByte((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())
//            byteList.appendByte((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())
//            byteList.appendByte((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())

            list.add((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())
            //list.add((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())
            // list.add((8.let { frequency = frequency shr it; frequency } and 0xff).toByte())

        }
        val encodedTextBytes = encodedText.toByteArray()

        // Emit the encoded text:
        for (b in encodedTextBytes) {
          //  byteList.appendByte(b)
            list.add(b)
        }
        list.toByteArray()
       // byteList
        return list.toByteArray()   //list.toByteArray()
        //list.toByteArray()
    }

    private fun computeByteListSize(frequencyMap: Map<Byte, Int>, encodedText: BitString): Int {
        return (MAGIC.size + BYTES_PER_CODE_WORD_COUNT_ENTRY + BYTES_PER_BIT_COUNT_ENTRY
                + frequencyMap.size * BYTES_PER_WEIGHT_MAP_ENTRY
                + encodedText.numberOfBytesOccupied)
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
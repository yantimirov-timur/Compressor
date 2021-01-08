package huffman


import java.lang.Byte.toUnsignedInt
import kotlin.experimental.and

class HuffmanDeserializer {
    class Result(val encodedText: BitString, val countMap: Map<Byte, Int>)

    /**
     * Десериализация
     */
    fun deserialize(data: ByteArray): Result {
        checkSignature(data)
        val numberOfCodeWords = extractNumberOfCodeWords(data)
        val numberOfBits = extractNumberOfEncodedTextBits(data)
        val frequencyMap = extractCountMap(data, numberOfCodeWords)
        val encodedText = extractEncodedText(data, frequencyMap, numberOfBits)

        return Result(encodedText, frequencyMap)
    }

    private fun checkSignature(data: ByteArray) {
        if (data.size < 4) {
            throw java.lang.IllegalStateException("No file type signature. The file is too short: " + data.size)
        }
        for (i in HuffmanSerializer.SIGN.indices) {
            if (data[i] != HuffmanSerializer.SIGN[i]) {
                throw IllegalStateException("Bad file type signature.")
            }
        }
    }

    private fun extractNumberOfCodeWords(data: ByteArray): Int {
        if (data.size < 8) {
            throw IllegalStateException("No number of code words. The file is too short: " + data.size)
        }
        var numberOfCodeWords = 0
        numberOfCodeWords = numberOfCodeWords or (toUnsignedInt(data[7]) shl 24)
        numberOfCodeWords = numberOfCodeWords or (toUnsignedInt(data[6]) shl 16)
        numberOfCodeWords = numberOfCodeWords or (toUnsignedInt(data[5]) shl 8)
        numberOfCodeWords = numberOfCodeWords or toUnsignedInt(data[4])

        return numberOfCodeWords
    }

    private fun extractNumberOfEncodedTextBits(data: ByteArray): Int {
        if (data.size < 12) {
            throw IllegalStateException("No number of encoded text bits. The file is too short: " + data.size)
        }

        var numberOfEncodedTextBits = 0
        numberOfEncodedTextBits = numberOfEncodedTextBits or (toUnsignedInt(data[11]) shl 24)
        numberOfEncodedTextBits = numberOfEncodedTextBits or (toUnsignedInt(data[10]) shl 16)
        numberOfEncodedTextBits = numberOfEncodedTextBits or (toUnsignedInt(data[9]) shl 8)
        numberOfEncodedTextBits = numberOfEncodedTextBits or toUnsignedInt(data[8])

        return numberOfEncodedTextBits
    }

    private fun extractCountMap(data: ByteArray, numberOfCodeWords: Int): Map<Byte, Int> {
        val countMap = mutableMapOf<Byte, Int>().toSortedMap()
        var dataByteIndex: Int = HuffmanSerializer.SIGN.size +
                HuffmanSerializer.BYTES_PER_BIT_COUNT_ENTRY +
                HuffmanSerializer.BYTES_PER_CODE_WORD_COUNT_ENTRY

        for (i in 0 until numberOfCodeWords) {
            val character = data[dataByteIndex++]
            val frequencyByte1 = data[dataByteIndex++]
            val frequencyByte2 = data[dataByteIndex++]
            val frequencyByte3 = data[dataByteIndex++]
            val frequencyByte4 = data[dataByteIndex++]
            var frequency = toUnsignedInt(frequencyByte1)

            frequency = frequency or (toUnsignedInt(frequencyByte2) shl 8)
            frequency = frequency or (toUnsignedInt(frequencyByte3) shl 16)
            frequency = frequency or (toUnsignedInt(frequencyByte4) shl 24)
            countMap[character] = frequency
        }

        return countMap
    }

    private fun extractEncodedText(
        data: ByteArray,
        frequencyMap: Map<Byte, Int>,
        numberOfEncodedTextBits: Int
    ): BitString {
        var omittedBytes: Int = HuffmanSerializer.SIGN.size +
                HuffmanSerializer.BYTES_PER_BIT_COUNT_ENTRY +
                HuffmanSerializer.BYTES_PER_CODE_WORD_COUNT_ENTRY
        omittedBytes += frequencyMap.size * HuffmanSerializer.BYTES_PER_WEIGHT_MAP_ENTRY
        val encodedText = BitString()
        var currentByteIndex = omittedBytes
        var currentBitIndex = 0

        for (bitIndex in 0 until numberOfEncodedTextBits) {
            val bit = data[currentByteIndex] and (1 shl currentBitIndex).toByte() != 0.toByte()

            encodedText.appendBit(bit)
            if (++currentBitIndex == java.lang.Byte.SIZE) {
                currentBitIndex = 0
                currentByteIndex++
            }
        }

        return encodedText
    }
}
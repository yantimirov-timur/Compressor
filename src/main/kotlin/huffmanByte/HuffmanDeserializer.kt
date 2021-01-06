import huffmanByte.BitString
import huffmanByte.HuffmanSerializer
import java.lang.IllegalStateException
import java.util.*
import kotlin.experimental.and

class HuffmanDeserializer {
    class Result internal constructor(
        encodedText: BitString,
        frequencyMap: Map<Byte, Int>
    ) {
        val encodedText: BitString = encodedText
        val countMap: Map<Byte, Int> = frequencyMap



    }

    /**
     * Deserializes and returns the data structures need for decoding the text.
     *
     * @param data the raw byte data previously serialised.
     * @return the data structures needed for decoding the text.
     */
    fun deserialize(data: ByteArray): Result {
        checkSignature(data)
        val numberOfCodeWords = extractNumberOfCodeWords(data)
        val numberOfBits = extractNumberOfEncodedTextBits(data)
        val frequencyMap = extractCountMap(
            data,
            numberOfCodeWords
        )
        val encodedText: BitString = extractEncodedText(
            data,
            frequencyMap,
            numberOfBits
        )
        return Result(encodedText, frequencyMap)
    }

    private fun checkSignature(data: ByteArray) {
        if (data.size < 4) {
            throw IllegalStateException("Invalid file format.")
        }
        for (i in 0 until HuffmanSerializer.MAGIC.size) {
            if (data[i] != HuffmanSerializer.MAGIC.get(i)) {
                throw IllegalStateException("Invalid file format.")
            }
        }
    }

    private fun extractNumberOfCodeWords(data: ByteArray): Int {
        if (data.size < 8) {
            throw IllegalStateException("Invalid file format.")
        }
        var numberOfCodeWords = 0
        numberOfCodeWords = numberOfCodeWords or (java.lang.Byte.toUnsignedInt(data[7]) shl 24)
        numberOfCodeWords = numberOfCodeWords or (java.lang.Byte.toUnsignedInt(data[6]) shl 16)
        numberOfCodeWords = numberOfCodeWords or (java.lang.Byte.toUnsignedInt(data[5]) shl 8)
        numberOfCodeWords = numberOfCodeWords or java.lang.Byte.toUnsignedInt(data[4])
        return numberOfCodeWords
    }

    private fun extractNumberOfEncodedTextBits(data: ByteArray): Int {
        if (data.size < 12) {
            throw IllegalStateException("Invalid file format.")
        }
        var numberOfEncodedTextBits = 0
        numberOfEncodedTextBits = numberOfEncodedTextBits or (java.lang.Byte.toUnsignedInt(data[11]) shl 24)
        numberOfEncodedTextBits = numberOfEncodedTextBits or (java.lang.Byte.toUnsignedInt(data[10]) shl 16)
        numberOfEncodedTextBits = numberOfEncodedTextBits or (java.lang.Byte.toUnsignedInt(data[9]) shl 8)
        numberOfEncodedTextBits = numberOfEncodedTextBits or java.lang.Byte.toUnsignedInt(data[8])
        return numberOfEncodedTextBits
    }

    private fun extractCountMap(
        data: ByteArray,
        numberOfCodeWords: Int
    ): Map<Byte, Int> {
        val countMap: MutableMap<Byte, Int> = TreeMap()
        try {
            var dataByteIndex: Int = HuffmanSerializer.MAGIC.size +
                    HuffmanSerializer.BYTES_PER_BIT_COUNT_ENTRY +
                    HuffmanSerializer.BYTES_PER_CODE_WORD_COUNT_ENTRY
            for (i in 0 until numberOfCodeWords) {
                val character = data[dataByteIndex++]
                val frequencyByte1 = data[dataByteIndex++]
                val frequencyByte2 = data[dataByteIndex++]
                val frequencyByte3 = data[dataByteIndex++]
                val frequencyByte4 = data[dataByteIndex++]
                var frequency = java.lang.Byte.toUnsignedInt(frequencyByte1)
                frequency = frequency or (java.lang.Byte.toUnsignedInt(frequencyByte2) shl 8)
                frequency = frequency or (java.lang.Byte.toUnsignedInt(frequencyByte3) shl 16)
                frequency = frequency or (java.lang.Byte.toUnsignedInt(frequencyByte4) shl 24)
                countMap[character] = frequency
            }
        } catch (ex: ArrayIndexOutOfBoundsException) {
            throw IllegalStateException("Invalid file format.")
        }
        return countMap
    }

    private fun extractEncodedText(
        data: ByteArray,
        frequencyMap: Map<Byte, Int>,
        numberOfEncodedTextBits: Int
    ): BitString {
        var omittedBytes: Int = HuffmanSerializer.MAGIC.size +
                HuffmanSerializer.BYTES_PER_BIT_COUNT_ENTRY +
                HuffmanSerializer.BYTES_PER_CODE_WORD_COUNT_ENTRY
        omittedBytes += frequencyMap.size *
                HuffmanSerializer.BYTES_PER_WEIGHT_MAP_ENTRY
        val encodedText = BitString()
        var currentByteIndex = omittedBytes
        var currentBitIndex = 0
        try {
            for (bitIndex in 0 until numberOfEncodedTextBits) {
                val bit = (data[currentByteIndex] and ((1 shl currentBitIndex).toByte())) != 0.toByte()
                encodedText.appendBit(bit)
                if (++currentBitIndex == java.lang.Byte.SIZE) {
                    currentBitIndex = 0
                    currentByteIndex++
                }
            }
        } catch (ex: ArrayIndexOutOfBoundsException) {
            throw IllegalStateException("Invalid file format.")
        }
        return encodedText
    }
}
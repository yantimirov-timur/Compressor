package huffmanByte

class HuffmanEncoder {

    /**
     * Кодирование входных данных по Хаффману
     */
    fun encode(map: Map<Byte, BitString>, text: ByteArray): BitString {
        val outputBitString = BitString()
        val textLength = text.size
        for (index in 0 until textLength) {
            val currentByte = text[index]
            val codeWord = map[currentByte]
            outputBitString.appendBitsFrom(codeWord!!)
        }
        return outputBitString
    }
}
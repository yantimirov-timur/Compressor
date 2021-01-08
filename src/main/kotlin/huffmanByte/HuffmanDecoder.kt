package huffmanByte

class HuffmanDecoder {

    /**
     * Декодирование входных данных
     */
    fun decode(tree: HuffmanTree, bits: BitString): ByteArray {
        val index = HuffmanTree.IntHolder()
        val bitStringLength = bits.length()
        val list = mutableListOf<Byte>()

        while (index.value < bitStringLength) {
            val character = tree.decodeBitString(index, bits)
            list.add(character)
        }

        return list.toByteArray()
    }
}
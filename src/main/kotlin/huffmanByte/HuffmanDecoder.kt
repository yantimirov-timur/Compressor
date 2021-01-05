package huffmanByte

class HuffmanDecoder {

    /**
     * Декодирование входных данных
     */
    fun decode(tree: HuffmanTree, bits: BitString): ByteArray {
        val index = HuffmanTree.IntHolder()
        val bitStringLength = bits.length()
        val list = mutableListOf<Byte>()
      //  val byteList = ByteList()
        while (index.value < bitStringLength) {
            val character = tree.decodeBitString(index, bits)
            list.add(character)
        //    byteList.appendByte(character)
        }
      //  val f = byteList.toByteArray()
        val c = list.toByteArray()
        return c
    }
}
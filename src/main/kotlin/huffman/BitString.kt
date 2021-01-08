package huffman


class BitString {
    /**
     *  Массив длинных значений для хранения битов.
     */
    private var storageLongs: LongArray

    /**
     * Текущий максимум битов, который можно хранить
     */
    private var storageCapacity: Int
    private var size = 0

    var numberOfBytesOccupied: Int = size / 8 + if (size % 8 == 0) 0 else 1

    /**
     * Создание пустых биотвых строк
     */
    constructor() {
        storageLongs = LongArray(DEFAULT_NUMBER_OF_LONGS)
        storageCapacity = storageLongs.size * BITS_PER_LONG
    }

    /**
     * Создание отдельного конструктора битовых строк с тем же содержимом, что и в параметре
     */
    constructor(toCopy: BitString) {
        size = toCopy.size
        storageCapacity = Math.max(size, DEFAULT_NUMBER_OF_LONGS * BITS_PER_LONG)
        val numberOfLongs = storageCapacity / BITS_PER_LONG +
                if (storageCapacity and MODULO_MASK == 0) 0 else 1
        storageLongs = toCopy.storageLongs.copyOf(numberOfLongs)
    }

    /**
     * Добавление бита
     */
    fun appendBit(bit: Boolean) {
        checkBitArrayCapacity(size + 1)
        writeBitImpl(size, bit)
        ++size
    }

    /**
     * Количество битов в билдере
     */
    fun length(): Int = size

    /**
     * Добавляет все биты в ' bitStringBuilder` в конец этого конструктора.
     */
    fun appendBitsFrom(bitStringBuilder: BitString) {
        checkBitArrayCapacity(size + bitStringBuilder.size)
        val otherSize = bitStringBuilder.size
        for (i in 0 until otherSize) {
            appendBit(bitStringBuilder.readBitImpl(i))
        }
    }

    fun readBit(index: Int): Boolean {
        checkAccessIndex(index)
        return readBitImpl(index)
    }

    /**
     * Удаление последнео бита
     */
    fun removeLastBit() {
        check(size != 0)
        size--
    }

    /**
     * Количество занятых байтов
     */

    fun toByteArray(): ByteArray {
        val numberOfBytes = size / java.lang.Byte.SIZE + if (size % java.lang.Byte.SIZE == 0) 0 else 1
        val byteArray = ByteArray(numberOfBytes)
        for (i in 0 until numberOfBytes) {
            byteArray[i] = ((storageLongs[i / java.lang.Long.BYTES] ushr java.lang.Byte.SIZE * (i % java.lang.Long.BYTES)) and 0xff).toByte()
        }
        return byteArray
    }

    override fun toString(): String {
        val sb = StringBuilder(size)
        for (i in 0 until size) {
            sb.append(if (readBitImpl(i)) '1' else '0')
        }
        return sb.toString()
    }

    private fun checkAccessIndex(index: Int) {
        check(size != 0) { "The bit string is empty." }
        if (index < 0) {
            throw IndexOutOfBoundsException(
                "The index is negative: $index."
            )
        }
        if (index >= size) {
            throw IndexOutOfBoundsException(
                "The bit index is too large (" + index + "). Must be at most " +
                        (size - 1) + "."
            )
        }
    }

    private fun readBitImpl(index: Int): Boolean {
        val longIndex = index / BITS_PER_LONG
        val bitIndex = index and MODULO_MASK
        val mask = 1L shl bitIndex
        return storageLongs[longIndex] and mask != 0L
    }

    private fun writeBitImpl(index: Int, bit: Boolean) {
        val longIndex = index / BITS_PER_LONG
        val bitIndex = index and MODULO_MASK
        if (bit) {
            val mask = 1L shl bitIndex
            storageLongs[longIndex] = storageLongs[longIndex] or mask
        } else {
            val mask = (1L shl bitIndex).inv()
            storageLongs[longIndex] = storageLongs[longIndex] and mask
        }
    }

    private fun checkBitArrayCapacity(requestedCapacity: Int) {
        if (requestedCapacity > storageCapacity) {
            val requestedWords1 = requestedCapacity / BITS_PER_LONG +
                    if (requestedCapacity and MODULO_MASK == 0) 0 else 1
            val requestedWords2 = 3 * storageCapacity / 2 / BITS_PER_LONG
            val selectedRequestedWords = Math.max(
                requestedWords1,
                requestedWords2
            )
            storageLongs = storageLongs.copyOf(selectedRequestedWords)
            storageCapacity = storageLongs.size * BITS_PER_LONG
        }
    }

    companion object {
        /**
         * Длина массива, хранящего биты.
         */
        private const val DEFAULT_NUMBER_OF_LONGS = 2

        /**
         * Используется для более эффективной арифметики по модулю при индексации определенного
        бита в long значении.
         */
        private const val MODULO_MASK = 63

        /**
         * Количество битов на long значение;
         */
        private const val BITS_PER_LONG = java.lang.Long.BYTES * java.lang.Byte.SIZE
    }
}
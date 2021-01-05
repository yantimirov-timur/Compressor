package huffmanByte

import java.util.*

class ByteList {
    private var data: ByteArray
    private var size = 0

    constructor() {
        data = ByteArray(8)
    }

    constructor(capacity: Int) {
        data = ByteArray(capacity)
    }

    fun appendByte(b: Byte) {
        ensureCapacity(size + 1)
        data[size++] = b
    }

    fun toByteArray(): ByteArray {
        return data.copyOfRange(0, size)
    }

    private fun ensureCapacity(requestedCapacity: Int) {
        if (requestedCapacity > data.size) {
            val selectedCapacity = requestedCapacity.coerceAtLeast(data.size * 2)
            data = data.copyOf(selectedCapacity)
        }
    }
}
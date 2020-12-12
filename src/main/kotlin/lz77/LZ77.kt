package lz77

import deflate.Compressor

/**
 * Алгоритм сжатия LZ77
 */
class LZ77 : Compressor<LZ77Node> {
    var node: LZ77Node? = null
    var compressedResult = mutableListOf<LZ77Node>()

    /**
     * Кодирование по LZ77
     */
    override fun encode(source: String): List<LZ77Node> {
        var buffer = ""
        var pos = 0
        var length = 0

        while (pos < source.length - 1) {
            if (source[pos] !in buffer) {
                buffer += source[pos]
                node = LZ77Node(0, 0, source[pos].toString())
                compressedResult.add(node!!)
                pos++
            } else {
                for (j in buffer.indices) {
                    if (buffer[j] == source[pos]) {
                        pos++
                        if (pos >= source.length) {
                            pos--
                            break
                        }
                        buffer += buffer[j]
                        length++
                    }
                }
                node = LZ77Node(pos - length, length, source[pos].toString())
                compressedResult.add(node!!)
                length = 0
            }
        }
        return compressedResult
    }
}
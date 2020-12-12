package lz77

import deflate.Compressor

class LZ77 : Compressor {
    var node: LZ77Node? = null
    var compressedResult = mutableListOf<LZ77Node>()

    override fun encode(str: String): List<LZ77Node> {
        var buffer = ""
        var pos = 0
        var lenght = 0

        while (pos < str.length - 1) {
            if (str[pos] !in buffer) {

                buffer += str[pos]

                node = LZ77Node(0, 0, str[pos])

                compressedResult.add(node!!)
                pos++
            } else {

                for (j in buffer.indices) {
                    if (buffer[j] == str[pos]) {
                        pos++
                        if (pos >= str.length) {
                            pos--
                            break
                        }

                        buffer += buffer[j]
                        lenght++
                    }
                }

                node = LZ77Node(pos - lenght, lenght, str[pos])
                compressedResult.add(node!!)
                lenght = 0

            }
        }
        return compressedResult

    }

}
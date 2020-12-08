package lz77

import deflate.Compressor

class LZ77 : Compressor {
    var node: Node? = null
    var compressedResult = mutableListOf<Node>()

    override fun encode(str: String): List<Node> {
        var buffer = ""
        var pos = 0
        var lenght = 0

        while (pos < str.length - 1) {
            if (str[pos] !in buffer) {

                buffer += str[pos]

                node = Node(0, 0, str[pos])

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

                node = Node(pos - lenght, lenght, str[pos])
                compressedResult.add(node!!)
                lenght = 0

            }
        }
        return compressedResult

    }

}
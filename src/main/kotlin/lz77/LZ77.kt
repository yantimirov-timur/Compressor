package lz77

import Compressor
import java.util.zip.Deflater

class LZ77 : Compressor {
    var node: Node? = null

//    var buffer = ""

    var compressedResult = mutableListOf<Node>()


    var d:Deflater = Deflater();

    fun lzCode(str: String): MutableList<Int>{
        var dictSize = 256

        val dictionary = mutableMapOf<String, Int>()

        (0 until dictSize).forEach { dictionary[it.toChar().toString()] = it }
        var w = ""
        val result = mutableListOf<Int>()
        for (c in str) {
            val wc = w + c
            if (dictionary.containsKey(wc))
                w = wc
            else {
                result.add(dictionary[w]!!)
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++)
                w = c.toString()
            }
        }

        // Output the code for w
        if (!w.isEmpty()) result.add(dictionary[w]!!)
        return result
    }



    /**fun lzEncode() {
        var buffer = ""
        var length = 0
        val str = "abacabacabadaca"

        for (i in 0..str.length) {
            if (str[i] !in buffer) {
                buffer += str[i]
                node = Node(0, 0, str[i])
            } else {
                if (str[i + 1] !in buffer) {
                    length++
                    node = Node(i, length, str[i + length])
                    buffer += str[i]
                } else {
                    length++
                }
            }
            compressedResult.add(node!!)

        }

    }*/


    override fun encode(str: String): String {

        return ""

    }


//    fun decode(encoded: String, source: Any): String {
//        TODO("Not yet implemented")
//    }


}
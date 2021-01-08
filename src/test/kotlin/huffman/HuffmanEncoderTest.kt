package huffman

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class HuffmanEncoderTest {

    @Test
    fun encode() {
        val inputFile = File("input.txt")
        val outputFile = File("output")

        val file = inputFile.readBytes()
        val weightMap = Parser().countFrequency(file)
        val encodeMap = HuffmanTree(weightMap).fillFrequencyTable()
        val encodedText = HuffmanEncoder().encode(encodeMap, file)
        val data: ByteArray = HuffmanSerializer().serialize(weightMap, encodedText)

        outputFile.writeBytes(data)

        assertTrue(outputFile.length() < inputFile.length())
    }
}
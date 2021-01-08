package huffman

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.io.FileReader

import java.io.BufferedReader

import java.io.IOException


internal class HuffmanDecoderTest {

    @Test
    fun decode() {
        val encodedFile = File("output")
        val decodedFile = File("encoded.txt")

        val inputData = encodedFile.readBytes()
        val result: HuffmanDeserializer.Result = HuffmanDeserializer().deserialize(inputData)
        val decoderTree = HuffmanTree(result.countMap)
        val decoder = HuffmanDecoder()
        val originalData = decoder.decode(decoderTree, result.encodedText)

        decodedFile.writeBytes(originalData)

        val sourceData = decodedFile.readText()
        val decodedData = File("input.txt").readText()

        assertTrue(sourceData == decodedData)

    }
}
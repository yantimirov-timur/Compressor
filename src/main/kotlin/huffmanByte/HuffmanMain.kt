package huffmanByte

import HuffmanDeserializer
import deflate.Compressor
import java.io.File

class HuffmanMain : Compressor {

    override fun encode(inputFile: File, encodedFile: File) {
        val file = inputFile.readBytes()
        val weightMap = Parser().countFrequency(file)
        val encodeMap = HuffmanTree(weightMap).fillFrequencyTable()
        val encodedText = HuffmanEncoder().encode(encodeMap, file)
        val data: ByteArray = HuffmanSerializer().serialize(weightMap, encodedText)

        encodedFile.writeBytes(data)
    }

    override fun decode(encodedFile: File, decodedFile: File) {
        val inputData = encodedFile.readBytes()
        val result: HuffmanDeserializer.Result = HuffmanDeserializer().deserialize(inputData)
        val decoderTree = HuffmanTree(result.countMap)
        val decoder = HuffmanDecoder()
        val originalData = decoder.decode(decoderTree, result.encodedText)

        decodedFile.writeBytes(originalData)
    }
}
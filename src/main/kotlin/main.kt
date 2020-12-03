import deflate.huffman.Huffman
import deflate.huffman.Parser
import lz77.LZ77

fun main() {
    val huffman = Huffman()
    val lZ77 = LZ77()

    val encoded = huffman.encode(Parser().input)
    val tree = huffman.huffmanTreeTraversal(huffman.codeTreeNodes)

    val decoded = huffman.decode(encoded, tree)

   // lZ77.lzEncode()
    lZ77.lzCode("abracadabra")

    println("Исходная строка: " + Parser().input)
    println("Размеры исходной строки: " + Parser().input.toByteArray().size * 8)
    println("Биты сжатой строки:  $encoded")
    println("Размеры сжатой строки:  ${encoded.length}")
    println("Расшифрованная строка: $decoded")

}
import huffman.Huffman
import huffman.Parser

fun main() {
    val huffman = Huffman()
    val encoded = huffman.huffmanEncode(Parser().input)
    val tree = huffman.huffmanTreeTraversal(huffman.codeTreeNodes)
    val decoded = Huffman().huffmanDecode(encoded,tree)

    println("Исходная строка: " + Parser().input)
    println("Размеры исходной строки: " + Parser().input.toByteArray().size * 8)
    println("Биты сжатой строки:  $encoded")
    println("Размеры сжатой строки:  ${encoded.length}")
    println("Расшифрованная строка: $decoded")
}
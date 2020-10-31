import huffman.Huffman
import huffman.Parser

fun main(args: Array<String>) {
    val encoded = Huffman().huffmanEncode(Parser().input)

    println("Исходная строка:" + Parser().input)
    println("Размеры исходной строки: " + Parser().input.toByteArray().size * 8)
    println("Биты сжатой строки:  $encoded")
    println("Размеры сжатой строки:  ${encoded.length}")
}
package huffman

import java.io.File

fun main(args: Array<String>) {
    val encoded = Huffman().huffmanEncode(Parser().input)


    println("Исходная строка:" + Parser().input)
  //  println("Таблица префиксных кодов: ${CodeTreeNode().codesMap}")
    println("Размеры исходной строки: " + Parser().input.toByteArray().size * 8)
    println("Биты сжатой строки:  $encoded")
    println("Размеры сжатой строки:  ${encoded.length}")
    // println("Раскодированная строка: $decoded")

}
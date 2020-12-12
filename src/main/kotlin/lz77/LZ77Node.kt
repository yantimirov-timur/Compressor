package lz77

/**
 * Класс для хранения узла в LZ77
 */
data class LZ77Node(var offset: Int, var length: Int, var next: String)
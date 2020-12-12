package huffman

/**
 * Класс, описывающий узлы и дерево Хаффмана
 */
data class HuffmanNode(
        var content: Char? = null,
        var weight: Int? = null,
        var leftChild: HuffmanNode? = null,
        var rightChild: HuffmanNode? = null) : Comparable<HuffmanNode> {

    constructor(content: Char?,
                weight: Int?
    ) : this(content, weight, leftChild = null, rightChild = null)

    override fun compareTo(other: HuffmanNode): Int {
        return other.weight!! - weight!!
    }

    /**
     * Запонение таблицы символов
     */
    fun fillCodesTable(freq: Set<Char>): Map<Char, String> {
        val codesMap = mutableMapOf<Char, String>().toSortedMap()
        for (key in freq) {
            codesMap[key] = findCodeForChar(key, "")
        }
        return codesMap
    }
    /**
     * Нахождение кодов для каждого символа (вспомогательный метод)
     */
    private fun findCodeForChar(char: Char, parentPath: String): String? {
        if (content == char)
            return parentPath
        else {
            if (leftChild != null) {
                val path = leftChild?.findCodeForChar(char, parentPath + 0)
                if (path != null)
                    return path
            }
            if (rightChild != null) {
                val path = rightChild?.findCodeForChar(char, parentPath + 1)
                if (path != null) {
                    return path
                }
            }
        }
        return null
    }
}
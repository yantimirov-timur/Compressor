package deflate.huffman

/**
 * Класс, описывающий узлы и дерево Хаффмана
 */
data class CodeTreeNode(
        var content: Char? = null,
        var weight: Int? = null,
        var leftChild: CodeTreeNode? = null,
        var rightChild: CodeTreeNode? = null) : Comparable<CodeTreeNode> {


    constructor(content: Char?,
                weight: Int?
    ) : this(content, weight, leftChild = null, rightChild = null)

    override fun compareTo(other: CodeTreeNode): Int {
        return other.weight!! - weight!!
    }

    var frequencies = Parser().frequencyTable
    val codesMap = mutableMapOf<Char, String>().toSortedMap()

    /**
     * Запонение таблицы символов(возможно обьединение)
     */
    fun fillCodesTable(): Map<Char, String> {
        for (key in frequencies.keys) {
            codesMap[key] = findCodeForChar(key, "")
        }
        return codesMap
    }

    /**
     * Нахождение кодов для каждого символа
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
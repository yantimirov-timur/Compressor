package huffman

class CodeTreeNode(_content: Char?, _weight: Int?) : Comparable<CodeTreeNode> {
    var content: Char? = null
    var weight: Int? = null
    var leftChild: CodeTreeNode? = null
    var rightChild: CodeTreeNode? = null

    var frequencies = Parser().frequencyTable
      val codesMap = mutableMapOf<Char, String>().toSortedMap()

    fun fillCodesTable():Map<Char,String> {
        for (key in frequencies.keys) {
            codesMap[key] = findCodeForChar(key, "")
        }
        return codesMap
    }

    init {
        content = _content
        weight = _weight
    }

    constructor(_content: Char?, _weight: Int?, _leftChild: CodeTreeNode?, _rightChild: CodeTreeNode?) :
            this(_content, _weight) {
        leftChild = _leftChild
        rightChild = _rightChild
    }

    override fun compareTo(other: CodeTreeNode): Int {
        return other.weight!! - weight!!
    }

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
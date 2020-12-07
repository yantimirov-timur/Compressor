class LZW{
    fun compress(uncompressed: String): MutableList<Int> {
        // Build the dictionary.
        var dictSize = 256
        val dictionary = mutableMapOf<String, Int>()
        (0 until dictSize).forEach { dictionary.put(it.toChar().toString(), it)}

        var w = ""
        val result = mutableListOf<Int>()
        for (c in uncompressed) {
            val wc = w + c
            if (dictionary.containsKey(wc))
                w = wc
            else {
                result.add(dictionary[w]!!)
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++)
                w = c.toString()
            }
        }

        // Output the code for w
        if (!w.isEmpty()) result.add(dictionary[w]!!)
        return result
    }
}
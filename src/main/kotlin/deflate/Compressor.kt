package deflate

interface Compressor<T> {
    /**
     * Кодирование
     */
    fun encode(source:String):List<T>

}
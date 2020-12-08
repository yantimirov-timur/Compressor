package deflate

interface Compressor {
    /**
     * Кодирование
     */
    fun encode(str:String):Any

}
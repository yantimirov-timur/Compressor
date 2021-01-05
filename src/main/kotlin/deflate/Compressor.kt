package deflate

import java.io.File

interface Compressor {

    /**
     * Кодирование
     */
    fun encode(inputFile: File, encodedFile: File)

    /**
     * Декодирование
     */
    fun decode(encodedFile: File,decodedFile: File)

}
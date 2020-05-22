package jmp123.demo

import jmp123.output.Audio
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.*


class MiniPlayerTest {
    @TempDir
    lateinit var tempDir: File
    @Test
    fun play(){
        val tempFile = File.createTempFile("asa", "tmp")
        tempFile.createNewFile()

        val outputStream = tempFile.outputStream()
        javaClass.getResourceAsStream("/mp3/stereo_kikuo.mp3").copyTo(outputStream)
        outputStream.close()

        Play.main(arrayOf(tempFile.absolutePath))
        Thread.sleep(10000)
    }
}
package fr.delthas.javamp3

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.IOException
import kotlin.math.sqrt

class MPEGTest {
    @Test
    @Throws(IOException::class)
    fun MPEG_I_layer_I() {
        compare(Res.MP1.MONO_KIKIUO, Res.MP1.MONO_KIKIUO_RAW, 0.05f)
        compare(Res.MP1.STEREO_KIKIUO, Res.MP1.STEREO_KIKIUO_RAW, 0.05f)
    }

    @Test
    @Throws(IOException::class)
    fun MPEG_I_layer_II() {
        compare(Res.MP2.MONO_KIKIUO, Res.MP2.MONO_KIKIUO_RAW, 0.05f)
        compare(Res.MP2.STEREO_KIKIUO, Res.MP2.STEREO_KIKIUO_RAW, 0.05f)
    }

    @Test
    @Throws(IOException::class)
    fun MPEG_I_layer_III() {
        compare(Res.MP3.JOINT_STEREO_KIKUO, Res.MP3.JOINT_STEREO_KIKUO_RAW, 5000f)
        compare(Res.MP3.MONO_KIKIUO, Res.MP3.MONO_KIKIUO_RAW, 0.05f)
        compare(Res.MP3.STEREO_KIKIUO, Res.MP3.STEREO_KIKIUO_RAW, 0.05f)
    }

    companion object {
        @Throws(IOException::class)
        private fun compare(name: String, refName: String, threshold: Float) {
            var result = 0.0
            var bytes = 0
            BufferedInputStream(MPEGTest::class.java.getResourceAsStream(refName)).use { `is` ->
                Sound(
                    BufferedInputStream(
                        MPEGTest::class.java.getResourceAsStream(name)
                    )
                ).use { ours ->
                    while (true) {
                        var read = `is`.read()
                        if (read == -1) {
                            break
                        }
                        var ref = read
                        read = `is`.read()
                        if (read == -1) {
                            break
                        }
                        ref = ref or (read shl 8)
                        ref = ref.toShort().toInt()
                        read = ours.read()
                        if (read == -1) {
                            Assertions.fail<Any>()
                        }
                        var our = read
                        read = ours.read()
                        if (read == -1) {
                            Assertions.fail<Any>()
                        }
                        our = our or (read shl 8)
                        our = our.toShort().toInt()
                        result += (our - ref) * (our - ref).toDouble()
                        bytes += 2
                    }
                    Assertions.assertEquals(ours.read(), -1)
                }
            }
            result = sqrt(result * 2 / bytes)
            //    assertTrue("RMS too large (discrepancy between our version and the reference one: " + result, result < threshold);
            Assertions.assertTrue(result < threshold)
        }
    }
}
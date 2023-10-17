package com.nikhiljain.blockiesview

import androidx.core.graphics.ColorUtils
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Class that encapsulates the preudo-random generation of block data and colors
 */
class BlockiesIconData(seed: String, size: Int) {
    private val randSeed: IntArray = intArrayOf(0, 0, 0, 0)

    /**
     * Retrieves the generated image data
     * @return Image data
     */
    lateinit var imageData: IntArray
        private set
    private val seed: String = seed.lowercase(Locale.getDefault())
    private var color = 0
    private var bgColor = 0
    private var spotColor = 0

    init {
        seedRand(this.seed)
        createIcon(size)
    }

    private fun seedRand(seed: String): IntArray {
        for (i in seed.indices) {
            randSeed[i % 4] = (randSeed[i % 4] shl 5) - randSeed[i % 4] + seed[i].code
        }
        return randSeed
    }

    private fun createIcon(size: Int) {
        color = createColor()
        bgColor = createColor()
        spotColor = createColor()
        imageData = createImageData(size)
    }

    private fun rand(): Double {
        val t = randSeed[0] xor (randSeed[0] shl 11)
        randSeed[0] = randSeed[1]
        randSeed[1] = randSeed[2]
        randSeed[2] = randSeed[3]
        randSeed[3] = randSeed[3] xor (randSeed[3] shr 19) xor t xor (t shr 8)
        val num = (randSeed[3] ushr 0).toDouble()
        val den = (1 shl 31 ushr 0).toDouble()
        return abs(num / den)
    }

    private fun createColor(): Int {
        val h = floor(rand() * 360).toFloat()
        val s = (rand() * 60 + 40).toFloat() / 100
        val l = ((rand() + rand() + rand() + rand()) * 25).toFloat() / 100
        return ColorUtils.HSLToColor(floatArrayOf(h, s, l))
    }

    private fun createImageData(size: Int): IntArray {
        val dataWidth = ceil((size / 2).toDouble()).toInt()
        val mirrorWidth = size - dataWidth
        val data = ArrayList<Int>()
        for (y in 0 until size) {
            val row = ArrayList<Int?>()
            for (x in 0 until dataWidth) {
                val r = rand() * 2.3
                val d = floor(r)
                val add = d.toInt()
                row.add(add)
            }
            val r: MutableList<Int?> = ArrayList()
            for (i in 0 until mirrorWidth) {
                r.add(row[i])
            }
            r.reverse()
            for (i in r.indices) {
                row.add(r[i])
            }
            for (i in row.indices) {
                data.add(row[i]!!)
            }
        }
        return toIntArray(data)
    }

    private fun toIntArray(list: List<Int>): IntArray {
        val ret = IntArray(list.size)
        var i = 0
        for (e in list) ret[i++] = e
        return ret
    }

    val colors: IntArray
        /**
         * Gets the generated colors in the form of an array of integers.
         * These are:
         *
         *
         * [0] color
         * [1] background color
         * [2] spot color
         *
         * @return
         */
        get() = intArrayOf(color, bgColor, spotColor)

    companion object {
        const val DEFAULT_SIZE = 10
    }
}
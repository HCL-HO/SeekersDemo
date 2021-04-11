package com.example.seekersgroup.util

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

object NumberUtil {
    private val SCALE: Int = 3

    fun Double.toRandomBuyPrice(): BigDecimal {
        val randomPip = getRandomPip()
        return this.toBigDecimal().plus(randomPip).setScale(SCALE, RoundingMode.HALF_UP).stripTrailingZeros()
    }

    fun Double.toRandomSellPrice(): BigDecimal {
        val bd = this.toBigDecimal()
        val randomPip = getRandomPip()
        return if (randomPip >= bd) {
            bd
        } else {
            bd.minus(randomPip)
        }.setScale(SCALE, RoundingMode.HALF_DOWN).stripTrailingZeros()
    }

    private fun getRandomPip(): BigDecimal {
        return ((1..10).random() / 10000.0).toBigDecimal()
    }
}
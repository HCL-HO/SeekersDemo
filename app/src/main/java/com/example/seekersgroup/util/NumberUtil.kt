package com.example.seekersgroup.util

import java.math.BigDecimal
import kotlin.math.pow

object NumberUtil {
    fun Double.toRandomBuyPrice(): BigDecimal {
        val randomPip = getRandomPip()
        return this.toBigDecimal().plus(randomPip)
    }

    fun Double.toRandomSellPrice(): BigDecimal {
        val bd = this.toBigDecimal()
        val randomPip = getRandomPip()
        return if (randomPip >= bd) {
            bd
        } else {
            bd.minus(randomPip)
        }
    }

    private fun getRandomPip(): BigDecimal {
        return ((1..10).random() / 10000.0).toBigDecimal()
    }
}
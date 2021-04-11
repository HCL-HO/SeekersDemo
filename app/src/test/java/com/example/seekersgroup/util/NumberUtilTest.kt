package com.example.seekersgroup.util

import com.example.seekersgroup.util.NumberUtil.toRandomBuyPrice
import com.example.seekersgroup.util.NumberUtil.toRandomSellPrice
import com.google.common.truth.Truth
import org.junit.Test

import org.junit.Assert.*

class NumberUtilTest {

    @Test
    fun toRandomBuyPrice() {
        val origin = 10.0
        val new = origin.toRandomBuyPrice()
        Truth.assertThat(new).isGreaterThan(origin.toBigDecimal())
    }

    @Test
    fun toRandomSellPrice() {
        val origin = 10.0
        val new = origin.toRandomSellPrice()
        Truth.assertThat(new).isLessThan(origin.toBigDecimal())
    }
}
package com.example.seekersgroup.util

import com.example.seekersgroup.util.NumberFormatUtil.toDisplayFormat
import com.google.common.truth.Truth
import org.junit.Test

class NumberFormatUtilTest {

    @Test
    fun toDisplayFormat_3_decimal_scale_to_2() {
        val result = "$991,999.13"
        val origin = 991999.132
        Truth.assertThat(origin.toDisplayFormat()).isEqualTo(result)
    }

    @Test
    fun toDisplayFormat_3digit_no_comma() {
        val result = "$991.13"
        val origin = 991.132
        Truth.assertThat(origin.toDisplayFormat()).isEqualTo(result)
    }

    @Test
    fun toDisplayFormat_addtrailingzero() {
        val result = "$991.00"
        val origin = 991.0
        Truth.assertThat(origin.toDisplayFormat()).isEqualTo(result)
    }

    @Test
    fun getChangeString_positive_change() {
        val old = 1.1
        val new = 2.1
        val result = "+0.91%"
        Truth.assertThat(NumberFormatUtil.getChangeString(old, new)).isEqualTo(result)
    }

    @Test
    fun getChangeString_negative_change() {
        val old = 3.3
        val new = 2.1
        val result = "-0.36%"
        Truth.assertThat(NumberFormatUtil.getChangeString(old, new)).isEqualTo(result)
    }

    @Test
    fun getChangeString_no_change() {
        val old = 3.3
        val new = 3.30000
        val result = "0.00%"
        Truth.assertThat(NumberFormatUtil.getChangeString(old, new)).isEqualTo(result)
    }
}
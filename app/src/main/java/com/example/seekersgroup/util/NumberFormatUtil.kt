package com.example.seekersgroup.util

import android.util.Log
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object NumberFormatUtil {
    const val CHANGE_SCALE = 2
    fun Double.toDisplayFormat(): String {
        val formatter = DecimalFormat("#,###.00")
        return "$${formatter.format(this)}"
    }

    fun getChangeString(old: Double, new: Double): String {
        val result = (new.toBigDecimal().minus(old.toBigDecimal())).divide(old.toBigDecimal(), CHANGE_SCALE, RoundingMode.HALF_UP)
        return when {
            result.compareTo(BigDecimal(0)) > 0 -> {
                "+${result}%"
            }
            result.compareTo(BigDecimal(0)) == 0 -> {
                "${result}%"
            }
            else -> "${result}%"
        }
    }
}
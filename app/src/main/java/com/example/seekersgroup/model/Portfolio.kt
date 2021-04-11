package com.example.seekersgroup.model

import com.example.seekersgroup.MainViewModel.Companion.KEY_RATE
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *  Holding users asset porfolio
 */
object Portfolio {
    data class Holding(
        val name: String, val amount: BigDecimal, val boughtAt: BigDecimal
    )

    private const val initSpending = 10000.0

    private val currentHoldings = mutableListOf<Holding>()

    /**
     *  From API response, assume user buys 10000 USD for each pair
     */
    fun addToHoldings(rateJsonObject: JSONObject) {
        rateJsonObject.keys().forEachRemaining { k ->
            val rateObj = rateJsonObject[k] as JSONObject
            if (rateObj.has(KEY_RATE)) {
                val holding =
                    Holding(
                        k,
                        initSpending.toBigDecimal() / (rateObj.getDouble(KEY_RATE)).toBigDecimal(),
                        rateObj.getDouble(KEY_RATE).toBigDecimal()
                    )
                currentHoldings.add(holding)
            }
        }
    }

    /**
     *  To update the porfoilio worth from API rates update
     */
    fun getEquityTotal(updatedRatesJsonObject: JSONObject): BigDecimal {
        var total = BigDecimal(0)
        currentHoldings.forEach {
            if (updatedRatesJsonObject.has(it.name)
                && updatedRatesJsonObject.getJSONObject(it.name).has(KEY_RATE)
            ) {
                total = total.add(
                    it.amount.times(
                        updatedRatesJsonObject.getJSONObject(it.name).getDouble(
                            KEY_RATE
                        ).toBigDecimal()
                    )
                )
            } else {
                total.plus(it.amount.times(it.boughtAt))
            }
        }
        return total.setScale(3, RoundingMode.HALF_UP)
    }
}
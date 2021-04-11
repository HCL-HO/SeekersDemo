package com.example.seekersgroup

import androidx.lifecycle.*
import com.example.seekersgroup.model.Failure
import com.example.seekersgroup.model.Portfolio
import com.example.seekersgroup.model.RatePairsResp
import com.example.seekersgroup.model.Success
import com.example.seekersgroup.ui.ForexListAdapter
import com.example.seekersgroup.util.DeferredExtension.awaitCatchException
import com.example.seekersgroup.util.Event
import com.example.seekersgroup.util.NumberFormatUtil
import com.example.seekersgroup.util.NumberUtil.toRandomBuyPrice
import com.example.seekersgroup.util.NumberUtil.toRandomSellPrice
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.math.BigDecimal

class MainViewModel : ViewModel() {
    companion object {
        const val KEY_RATE = "rate"
    }

    // TODO use string.xml
    private val UNEXPECTED: String ="Unexpected Error"
    private var isPolling: Boolean = false
    private val _error = MutableLiveData<Event<String>>()
    private val __forexRateList = MutableLiveData<JSONObject>()
    private val repository = Repository.get()
    var initRatings: JSONObject? = null

    val error: LiveData<Event<String>>
        get() {
            return _error
        }

    val equityState = MutableLiveData<BigDecimal>()

    val forexRowDatas = Transformations.map(__forexRateList) {
        if (initRatings == null) {
            initRatings = it
            Portfolio.addToHoldings(it)
        }

        val res = mutableListOf<ForexListAdapter.ForexRowData>()
        it.keys().forEachRemaining { k ->
            val rateObj = it[k] as JSONObject
            if (rateObj.has(KEY_RATE)) {
                val change = getChange(k, rateObj.getDouble(KEY_RATE))
                res.add(
                    ForexListAdapter.ForexRowData(
                        k,
                        change,
                        (rateObj[KEY_RATE] as Double).toRandomSellPrice(),
                        (rateObj[KEY_RATE] as Double).toRandomBuyPrice()
                    )
                )
            }
        }
        equityState.postValue(Portfolio.getEquityTotal(it))
        return@map Event(res.toTypedArray())
    }

    private fun getChange(k: String, price: Double): String {
        initRatings?.let {
            return if (!it.has(k)) {
                it.put(k, price)
                "0.0%"
            } else {
                val originPrice = it.getJSONObject(k).getDouble(KEY_RATE)
                NumberFormatUtil.getChangeString(originPrice, price)
            }
        } ?: return "0.0%"
    }


    /**
     *  Get Pairs from API
     */
    fun queryList() {
        viewModelScope.launch {
            repository.getSupportedPairsAsync().awaitCatchException().let {
                when (it) {
                    is Success -> {
                        it.value.also { r ->
                            queryRates(r)
                        }

                    }
                    is Failure -> {
                        Timber.e(it.reason)
                        _error.postValue(Event(it.reason.message ?: UNEXPECTED))
                    }
                }
            }
        }
    }

    /**
     *  polling from API to update constantly
     *  Minimum interval set to 5000 milisec
     */
    fun pollForexData(interval: Long = 5000) {
        isPolling = true
        val delay = if (interval < 5000) 5000 else interval
        viewModelScope.launch {
            while (isPolling) {
                delay(delay)
                queryList()
            }
        }
    }

    /*
    *   stop polling from API
    * */
    fun stopPolling() {
        isPolling = false
    }


    /**
     *  query rates for pairs
     */
    private suspend fun queryRates(value: RatePairsResp) {
        val parms = value.supportedPairs.joinToString(",")
        repository.getExchangeRatesAsync(parms).awaitCatchException().also { sgResult ->
            when (sgResult) {
                is Success -> {
                    Timber.d(Gson().toJson(sgResult.value.rates))
                    sgResult.value.rates?.let {
                        __forexRateList.postValue(it)
                    }
                }
                is Failure -> {
                    Timber.e(sgResult.reason)
                    _error.postValue(Event(sgResult.reason.message ?: UNEXPECTED))
                }
            }
        }
    }
}
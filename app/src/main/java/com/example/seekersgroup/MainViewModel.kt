package com.example.seekersgroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekersgroup.model.Failure
import com.example.seekersgroup.model.RatesResp
import com.example.seekersgroup.model.Success
import com.example.seekersgroup.util.DeferredExtension.awaitCatchException
import com.example.seekersgroup.util.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _error = MutableLiveData<Event<String>>()
    private val _forexList = MutableLiveData<Event<RatesResp>>()
    val forexList: LiveData<Event<RatesResp>>
        get() {
            return _forexList
        }

    val error: LiveData<Event<String>>
        get() {
            return _error
        }


    fun queryList() {
        viewModelScope.launch {
            repository.getSupportedPairsAsync().awaitCatchException().let {
                when (it) {
                    is Success -> {
                        it.value
                    }
                    is Failure -> {
                    }
                }
            }
        }
    }
}
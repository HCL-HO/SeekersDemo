package com.example.seekersgroup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.seekersgroup.MainViewModel
import com.example.seekersgroup.R
import com.example.seekersgroup.Repository
import com.example.seekersgroup.util.NumberFormatUtil.toDisplayFormat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ForexListAdapter
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupList()
        observe()
        viewmodel.queryList()
    }

    override fun onResume() {
        super.onResume()
        viewmodel.pollForexData(5000)
    }

    private fun observe() {
        viewmodel.forexRowDatas.observe(this) { e ->
            e.getContentIfNotHandled()?.let {
                Timber.d(Gson().toJson(it))
                adapter.setData(it)
                txt_balance.text = (it.size * 10000).toDouble().toDisplayFormat()
            }
        }
        viewmodel.equityState.observe(this) { e ->
            txt_equity.text = e.toDouble().toDisplayFormat()
        }
    }

    private fun setupList() {
        forex_list.adapter = ForexListAdapter().apply {
            adapter = this
        }
    }

    override fun onPause() {
        super.onPause()
        viewmodel.stopPolling()
    }


}
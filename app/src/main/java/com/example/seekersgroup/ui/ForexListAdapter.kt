package com.example.seekersgroup.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seekersgroup.R
import com.example.seekersgroup.databinding.VhForexItemBinding
import java.math.BigDecimal

class ForexListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    private var data = arrayListOf<ForexListItem>()

    fun setData(mData: Array<ForexRowData>) {
        data = arrayListOf()
        data.add(ForexListItem.Header)
        data.addAll(mData.map {
            ForexListItem.Item(it)
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_header, parent, false)
                Header(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_forex_item, parent, false)
                ForexItemViewHolder(VhForexItemBinding.bind(v))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ForexItemViewHolder -> {
                holder.bind(data[position] as ForexListItem.Item)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ForexListItem.Header -> {
                TYPE_HEADER
            }
            else -> {
                TYPE_ITEM
            }
        }
    }

    class Header(view: View) : RecyclerView.ViewHolder(view)
    class ForexItemViewHolder(val binding: VhForexItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ForexListItem.Item) {
            val rowData = data.data
            binding.txtSymbol.text = rowData.symbol
            binding.txtChange.text = rowData.change
            binding.txtSell.text = rowData.sell.toPlainString()
            binding.txtBuy.text = rowData.buy.toPlainString()
            if (rowData.change.startsWith("-")) {
                binding.txtChange.setTextColor(Color.RED)
            } else {
                binding.txtChange.setTextColor(Color.GREEN)
            }
        }
    }

    sealed class ForexListItem {
        object Header : ForexListItem()
        data class Item(val data: ForexRowData) : ForexListItem()
    }


    class ForexRowData(
        val symbol: String, val change: String, val sell: BigDecimal, val buy: BigDecimal
    )
}


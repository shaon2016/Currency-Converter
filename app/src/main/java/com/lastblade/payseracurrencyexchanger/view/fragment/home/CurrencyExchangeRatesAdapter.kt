package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastblade.payseracurrencyexchanger.R
import com.lastblade.payseracurrencyexchanger.data.model.CurrencyUnitRate
import com.lastblade.payseracurrencyexchanger.databinding.RvItemRowBinding

class CurrencyExchangeRatesAdapter(
    private var unitCurrencyRates: List<CurrencyUnitRate> = mutableListOf(),
    private var amount: Double = 0.0,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_TYPE_EMPTY = 1
    private val ITEM_TYPE_VALUE = 2

    fun updateAmount(amount: Double) {
        this.amount = amount
        notifyDataSetChanged()
    }

    fun updateCurrencyList(unitCurrencyRates: List<CurrencyUnitRate>) {
        this.unitCurrencyRates = unitCurrencyRates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_EMPTY -> return EmptyViewHolder(RvItemRowBinding.inflate(LayoutInflater.from(
                parent.context), parent, false))
            ITEM_TYPE_VALUE -> return CurrencyRatesViewHolder(RvItemRowBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false))
        }
        return EmptyViewHolder(RvItemRowBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    override fun getItemCount() = unitCurrencyRates.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.bind()
        } else if (holder is CurrencyRatesViewHolder) {
            holder.bind(amount, unitCurrencyRates[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (unitCurrencyRates.isEmpty()) {
            ITEM_TYPE_EMPTY;
        } else ITEM_TYPE_VALUE;
    }


    class CurrencyRatesViewHolder(val view: RvItemRowBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(amount: Double, unitRate: CurrencyUnitRate) {
            val conversion = String.format("%.3f", unitRate.unitRate * amount)
            view.tvExchangeRates.text = "${unitRate.toCurrency}\n${conversion}"
        }

    }

    class EmptyViewHolder(val view: RvItemRowBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind() {
            view.tvExchangeRates.text =
                view.root.context.getString(R.string.adapter_empty_text_no_exchange_rates_found)
        }
    }

}
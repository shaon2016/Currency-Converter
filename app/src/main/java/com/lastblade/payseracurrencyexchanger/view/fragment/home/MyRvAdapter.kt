package com.lastblade.payseracurrencyexchanger.view.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lastblade.payseracurrencyexchanger.data.model.MyBalanceModel
import com.lastblade.payseracurrencyexchanger.databinding.RvItemRowBinding

class MyRvAdapter(val items: ArrayList<MyBalanceModel>) :
    RecyclerView.Adapter<MyRvAdapter.MyRvVH>() {

    inner class MyRvVH(val v: RvItemRowBinding) : RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRvVH {
        return MyRvVH(RvItemRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MyRvVH, position: Int) {
        val item = items[holder.adapterPosition]
        holder.v.text.text = "${item.value} ${item.currencyStr}"
    }

    override fun getItemCount() = items.size
}
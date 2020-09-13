package com.ovssystems.productcalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView
import com.ovssystems.productcalculator.databinding.BasketHistoryItemBinding
import com.ovssystems.productcalculator.model.BasketHistoryModel
import com.ovssystems.productcalculator.productDataGlobal


class BasketHistoryListAdapter: OmegaRecyclerView.Adapter<BasketHistoryListAdapter.BasketHistoryItemHolder>() {

    private lateinit var basketHistoryList: MutableList<BasketHistoryModel>

    init {
        updateBasket()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHistoryItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = BasketHistoryItemBinding.inflate(inflater, parent, false)
        return BasketHistoryListAdapter.BasketHistoryItemHolder(binder.root)
    }

    override fun onBindViewHolder(holder: BasketHistoryItemHolder, position: Int) {
        holder.binding?.basketHistory = basketHistoryList[position]
    }

    override fun getItemCount(): Int {
        return basketHistoryList.size
    }

    fun updateBasket() {
        basketHistoryList = productDataGlobal.db.basketHistoryList as MutableList<BasketHistoryModel>
    }

    class BasketHistoryItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
            var  binding: BasketHistoryItemBinding? = DataBindingUtil.bind(itemView)
    }
}
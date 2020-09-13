package com.ovssystems.productcalculator.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ovssystems.productcalculator.R
import com.ovssystems.productcalculator.adapters.BasketHistoryListAdapter
import kotlinx.android.synthetic.main.fragment_basket_history.*


class BasketHistoryFragment : Fragment(R.layout.fragment_basket_history) {
    private lateinit var basketHistoryListAdapter: BasketHistoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        basketHistoryListAdapter = BasketHistoryListAdapter()
        basket_history_view.adapter = basketHistoryListAdapter
        basketHistoryListAdapter.updateBasket()

    }


}
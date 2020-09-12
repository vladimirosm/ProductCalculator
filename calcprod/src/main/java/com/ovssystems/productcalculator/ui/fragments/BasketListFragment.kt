package com.ovssystems.productcalculator.ui.fragments

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView
import com.ovssystems.productcalculator.ProductAddActivity
import com.ovssystems.productcalculator.R
import com.ovssystems.productcalculator.adapters.BasketListAdapter
import com.ovssystems.productcalculator.productDataGlobal
import kotlinx.android.synthetic.main.fragment_basket_list.*

class BasketListFragment : Fragment(R.layout.fragment_basket_list) {
    private lateinit var basketListView: OmegaRecyclerView
    private lateinit var basketListAdapter: BasketListAdapter

    override fun onStart() {
        super.onStart()
        initFunc()
        setHasOptionsMenu(true)
    //    APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    private fun initFunc() {

        basketListView = basket_item_view
        basketListAdapter = BasketListAdapter()
        basketListView.adapter = basketListAdapter
        basketListAdapter.updateBasket()
        fab_add_shipping.setOnClickListener { v-> onAddItemsToBasketClick(v) }
        busket_done_shipping.setOnClickListener { onBasketDoneShipping() }
    }

    private fun onBasketDoneShipping() {

        basketListAdapter.shoppedList.forEach {
            if (it.checked) productDataGlobal.db.updatePurchased(it._id,it.date_purchased)
        }
        basketListAdapter.updateBasket()
        basketListAdapter.notifyDataSetChanged()
    }

    private fun onAddItemsToBasketClick(view: View) {
        val intent = Intent(activity, ProductAddActivity::class.java);
        startActivity(intent)
 //      view.findNavController().navigate(R.id.action_basketListFragment_to_productsAddFragment)

    }
}


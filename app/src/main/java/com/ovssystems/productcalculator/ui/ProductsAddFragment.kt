package com.ovssystems.productcalculator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ovssystems.productcalculator.APP_ACTIVITY
import com.ovssystems.productcalculator.R
import com.ovssystems.productcalculator.adapters.ProductsAddAdapter
import com.ovssystems.productcalculator.model.ProductModel
import com.ovssystems.productcalculator.productDataGlobal
import kotlinx.android.synthetic.main.basket_add_items.*
import java.util.*

class ProductsAddFragment : Fragment(R.layout.basket_add_items) {

    lateinit var productAddListAdapter : ProductsAddAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
         productAddListAdapter = ProductsAddAdapter()
        basket_item_add_list.adapter = productAddListAdapter
        product_add_done_button.setOnClickListener { onDoneProduct() }
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            onDoneProduct()
        }
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onDoneProduct()
    {
        val products: MutableList<ProductModel> = ArrayList<ProductModel>()
        productAddListAdapter.selectedProductList.forEach{
            if (it.name.isNotEmpty()) products.add( productDataGlobal.db.productByName(it.name) )
        }
        productDataGlobal.db.addProductsToBusket(products)

        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onStop() {
        super.onStop()
    }
}
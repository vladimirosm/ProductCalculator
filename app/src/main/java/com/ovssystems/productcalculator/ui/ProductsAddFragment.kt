package com.ovssystems.productcalculator.ui

import android.Manifest.permission.INTERNET
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ovssystems.productcalculator.APP_ACTIVITY
import com.ovssystems.productcalculator.R
import com.ovssystems.productcalculator.adapters.ProductsAddAdapter
import com.ovssystems.productcalculator.model.ProductModel
import com.ovssystems.productcalculator.productDataGlobal
import kotlinx.android.synthetic.main.basket_add_items.*
import java.util.*

class ProductsAddFragment : Fragment(R.layout.basket_add_items) {

    lateinit var productAddListAdapter: ProductsAddAdapter
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

        btn_speech_recognizer.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> Toast.makeText(APP_ACTIVITY, "DOWN", Toast.LENGTH_SHORT).show();
                    MotionEvent.ACTION_UP -> Toast.makeText(APP_ACTIVITY, "UP", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        })


        if ((ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED)
            and (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                INTERNET
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
// TODO: show | hide voce button
        }
    }

    private fun onDoneProduct() {
        val products: MutableList<ProductModel> = ArrayList<ProductModel>()
        productAddListAdapter.selectedProductList.forEach {
            if (it.name.isNotEmpty())
                products.add(productDataGlobal.db.productByName(it.name))
        }
        productDataGlobal.db.addProductsToBasket(products)

        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onStop() {
        super.onStop()
    }
}
package com.ovssystems.productcalculator

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ovssystems.productcalculator.databinding.ActivityMainBinding
import com.ovssystems.productcalculator.model.ProductCalculatorDataModel
import com.ovssystems.productcalculator.ui.fragments.BasketListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY=this
    }

    override fun onStart() {
        super.onStart()
        initFields()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        replaceFragment (BasketListFragment(), false)
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        setSupportActionBar(mToolbar)
        productDataGlobal=ProductCalculatorDataModel(this)
    }

    fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
        /* Функция расширения для AppCompatActivity, позволяет устанавливать фрагменты */
        if (addStack) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.data_container,
                    fragment
                ).commit()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.data_container,
                    fragment
                ).commit()
        }
    }
    /** Функция скрывает клавиатуру */
    fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}
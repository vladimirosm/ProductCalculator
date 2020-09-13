package com.ovssystems.productcalculator


import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.ovssystems.productcalculator.databinding.ActivityMainBinding
import com.ovssystems.productcalculator.model.ProductCalculatorDataModel


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    private lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY=this

        productDataGlobal= ProductCalculatorDataModel(this)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    override fun onStart() {
        super.onStart()
        initFields()
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        setSupportActionBar(mToolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.basket_list_fragment, R.id.basket_history_fragment))
        setupActionBarWithNavController( navController, appBarConfiguration)
        NavigationUI.setupWithNavController(mBinding.bottomNavigation, navController);
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
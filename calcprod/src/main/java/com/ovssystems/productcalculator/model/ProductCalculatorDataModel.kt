package com.ovssystems.productcalculator.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ovssystems.productcalculator.dbHelper.DbHelper

class ProductCalculatorDataModel(context: Context) : ViewModel()
{
    var  db  = DbHelper(context)
}
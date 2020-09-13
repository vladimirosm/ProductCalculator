package com.ovssystems.productcalculator.model

import java.text.DateFormat
import java.util.*

data class BasketHistoryModel( var data: UInt,  var productCount:UInt)
{
    fun dataName():String {
        val date = Date( ( data.toLong()) *1000L   )
        return  DateFormat.getDateInstance().format(date);
    }
    fun productCountString() = productCount.toString()
}
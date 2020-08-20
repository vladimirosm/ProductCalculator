package com.ovssystems.productcalculator.model

import java.util.*

data class BasketProductModel(
    var _id: Long = 0,
    var item_id: Int = 0,
    var count: Int = 0,
    var date_purchased: Int = 0,
    var expired: Int = 0,
    var name: String = "",
    var note: String = ""
) {
    var checked: Boolean = false
        set(value) {
            field = value
            date_purchased = if (value)  Date().time.toInt()  else 0
        }

}
package com.ovssystems.productcalculator.data

import android.provider.BaseColumns

object BasketTableEntry : BaseColumns {
    const val TABLE_NAME = "basket"
    const val _ID = BaseColumns._ID
    const val COLUMN_ITEM_ID = "item_id"
    const val COLUMN_COUNT = "count"
    const val COLUMN_DATE_PURCHASED = "date_purchased"
    const val COLUMN_NOTE = "note"
    const val COLUMN_ITEM_NAME = "item_name"
    const val COLUMN_GROUP_ID = "group_id"
}
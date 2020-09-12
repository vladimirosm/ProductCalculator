package com.ovssystems.productcalculator.dbHelper.data

import android.provider.BaseColumns

object ProductTableEntry : BaseColumns {
    const val TABLE_NAME = "products"
    const val _ID = BaseColumns._ID
    const val COLUMN_NAME = "name"
    const val COLUMN_GROUP_ID = "group_id"
    const val COLUMN_PERIODIC = "periodic"
    const val COLUMN_EXPIRED = "expired"
}
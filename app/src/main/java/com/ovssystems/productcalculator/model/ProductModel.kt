package com.ovssystems.productcalculator.model

data class ProductModel(var name: String = "", var groupId: Int = 0, var id: Long =0)
{
    fun onProductTextChanged(text: CharSequence? )
    {
        this.name = text.toString()
    }
     override fun toString():String = name
}
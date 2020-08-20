package com.ovssystems.productcalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView
import com.ovssystems.productcalculator.APP_ACTIVITY
import com.ovssystems.productcalculator.databinding.ProductItemEditBinding
import com.ovssystems.productcalculator.model.ProductModel
import com.ovssystems.productcalculator.productDataGlobal
import java.util.*


class ProductsAddAdapter : OmegaRecyclerView.Adapter<ProductsAddAdapter.AddItemHolder>() {
    var selectedProductList: MutableList<ProductModel> = ArrayList<ProductModel>()
    private var mProductAutoComplList: ArrayList<ProductModel> = ArrayList<ProductModel>()

    init {
        prepareAutocompleteAdapter()
        selectedProductList.add(ProductModel())
    }

    private fun prepareAutocompleteAdapter(newItemString : String? =null) {
        mProductAutoComplList.clear()
        mProductAutoComplList.addAll( productDataGlobal.db.products )
        if (!newItemString.isNullOrEmpty()) mProductAutoComplList.add(ProductModel(newItemString))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAddAdapter.AddItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ProductItemEditBinding.inflate(inflater, parent, false)
        return AddItemHolder(binder.root)
    }

    override fun getItemCount(): Int {
        return selectedProductList.size
    }

    override fun onBindViewHolder(holder: ProductsAddAdapter.AddItemHolder, position: Int) {
        holder.binding?.product = selectedProductList[position]
        holder.binding?.editProductName?.doOnTextChanged { text, start, before, count ->
            onProductTextChanged(
                holder.adapterPosition,
                text.toString()
            )
        }

        // Обработчик щелчка
        holder.binding?.editProductName?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, id ->
                val selectedItem = parent.getItemAtPosition(position) as ProductModel
                // Выводим выбранное слово
//                Toast.makeText(APP_ACTIVITY, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
                onProductSelected(holder.adapterPosition, selectedItem)
            }


        val allProductAdapter = ArrayAdapter<ProductModel>(
            APP_ACTIVITY,
            android.R.layout.simple_dropdown_item_1line
            ,productDataGlobal.db.products
        )
        holder.binding?.editProductName?.setAdapter(allProductAdapter)
        // Минимальное число символов для показа выпадающего списка
        holder.binding?.editProductName?.threshold = 1
    }

    private fun onProductSelected(position: Int, selectedItem: ProductModel) {
        selectedProductList[position] = selectedItem
        notifyItemChanged(position)

        if (selectedProductList.last().name.isNotEmpty()) {
            selectedProductList.add(ProductModel())
            notifyItemInserted(selectedProductList.lastIndex)
        }
    }

    private fun onProductRemove(position: Int)
    {
        if (position >= selectedProductList.size ) return;

        selectedProductList.removeAt(position);
        notifyItemRemoved(position)
        if (  selectedProductList.size <1 ) {
            selectedProductList.add(ProductModel())
           notifyItemInserted(0)
        }
    }
    private fun onProductTextChanged(position: Int, text: String?) {

        if (selectedProductList.last().name.isNotEmpty()) {
            selectedProductList.add(ProductModel())
            notifyItemInserted(selectedProductList.lastIndex)
        }
    }

    inner class AddItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding: ProductItemEditBinding? = DataBindingUtil.bind(itemView)
        init {
            binding?.btnProductRemove?.setOnClickListener { onProductRemove(adapterPosition) }


        }
    }
}
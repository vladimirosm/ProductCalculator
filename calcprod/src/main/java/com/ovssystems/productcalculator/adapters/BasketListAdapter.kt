package com.ovssystems.productcalculator.adapters


import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omega_r.libs.omegarecyclerview.OmegaRecyclerView
import com.ovssystems.productcalculator.databinding.BasketProductItemBinding
import com.ovssystems.productcalculator.model.BasketProductModel
import com.ovssystems.productcalculator.productDataGlobal


open class BasketListAdapter :
    OmegaRecyclerView.Adapter<BasketListAdapter.BasketItemHolder>()

{
    lateinit var shoppedList: MutableList<BasketProductModel>

    init {
        updateBasket();
    }

    fun updateBasket() {
        shoppedList = productDataGlobal.db.basketList as MutableList<BasketProductModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = BasketProductItemBinding.inflate(inflater, parent, false)
        return BasketItemHolder(binder.root)
    }

    override fun onBindViewHolder(holder: BasketItemHolder, position: Int) {
        holder.binding?.product = shoppedList[position]
        holder.strikeText(shoppedList[holder.adapterPosition].checked)
    }

    override fun getItemCount(): Int {
        return shoppedList.size
    }

    inner class BasketItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding: BasketProductItemBinding? = DataBindingUtil.bind(itemView)

        init {
            binding?.productItemChecker?.setOnClickListener {
               val checked = (it as CheckBox).isChecked
                strikeText(checked)
                shoppedList[adapterPosition].checked = checked
            }
        }
        internal fun strikeText(striked: Boolean =false)
        {
            val paintFlags = binding?.shoppedItemProductText?.paintFlags ?: 0;
            binding?.shoppedItemProductText?.paintFlags = if (striked) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG;
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv();
            }
        }
    }

    class HeaderHolder(itemView: View) : OmegaRecyclerView.ViewHolder(itemView) {
        var header: TextView? = itemView as TextView?
    }
}
package com.artyomefimov.mystorage.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.list_item_product.view.*

class ProductListAdapter(
    private var productList: RealmResults<Product>,
    private val onItemClickAction: (product: Product) -> Unit) :
    RealmRecyclerViewAdapter<Product, ProductListViewHolder>(productList, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false)
        return ProductListViewHolder(itemView, onItemClickAction)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) =
        holder.bind(productList[position])
}

class ProductListViewHolder(
    itemView: View,
    private val onItemClickAction: (product: Product) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product?) {
        with(itemView) {
            if (product != null) {
                list_item_product_name.text = product.name
                list_item_product_price.text = product.price.toString()

                setOnClickListener {
                    onItemClickAction(product)
                }
            }
        }
    }
}
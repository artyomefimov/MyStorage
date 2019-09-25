package com.artyomefimov.mystorage.view.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.view.utils.loadImageFrom
import kotlinx.android.synthetic.main.list_item_product.view.*

class ProductListAdapter(
    var productList: List<Product>,
    private val onItemClickAction: (product: Product) -> Unit,
    private val loadImage: (product: Product, imageView: ImageView) -> Unit
) :
    RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false)
        return ProductListViewHolder(itemView, onItemClickAction, loadImage)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) =
        holder.bind(productList[position])
}

class ProductListViewHolder(
    itemView: View,
    private val onItemClickAction: (product: Product) -> Unit,
    private val loadImage: (product: Product, imageView: ImageView) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product?) {
        with(itemView) {
            if (product != null) {
                list_item_product_name.text = product.name
                list_item_product_price.text = product.price.toString()
                loadImage(product, list_item_product_image)

                setOnClickListener {
                    onItemClickAction(product)
                }
            }
        }
    }
}
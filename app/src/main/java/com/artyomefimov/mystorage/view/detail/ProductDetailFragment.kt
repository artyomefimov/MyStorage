package com.artyomefimov.mystorage.view.detail

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.detail.ProductDetailContract
import com.artyomefimov.mystorage.presenter.detail.ProductDetailPresenter
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment(
    private val productPresenter: ProductDetailPresenter = ProductDetailPresenter()
) : Fragment(), ProductDetailContract.View {
    companion object {
        private const val PRODUCT = "product"

        @JvmStatic
        fun newInstance(product: Product) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PRODUCT, product)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product = arguments?.getSerializable(PRODUCT) as Product
        toolbar_layout?.title = product.name

        productPresenter.product = product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_product_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productPresenter.attach(this)

    }

    override fun onDestroy() {
        super.onDestroy()

        productPresenter.saveProduct { product ->
            // todo implement update service
            val intent = Intent(activity, Service::class.java).apply {
                putExtra(PRODUCT, product)
            }
            activity?.startService(intent)
        }
    }
}
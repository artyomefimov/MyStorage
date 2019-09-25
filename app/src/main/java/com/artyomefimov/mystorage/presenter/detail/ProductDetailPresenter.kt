package com.artyomefimov.mystorage.presenter.detail

import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.repository.ProductRepository

class ProductDetailPresenter (
    private val productRepository: ProductRepository
) : ProductDetailContract.Presenter {

    var product: Product = Product()

    override fun attach(view: ProductDetailContract.View) {

    }

    override fun detach() {

    }

    override fun saveProduct(callUpdateService: (product: Product) -> Unit) {
        //callUpdateService(product)
    }
}